package com.bkbwongo.core.ebaasa.accountsmgt.service;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountLinkDto;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountMappingDto;
import com.bkbwongo.core.ebaasa.accountsmgt.jpa.models.TAccountMapping;
import com.bkbwongo.core.ebaasa.accountsmgt.repository.TAccountMappingRepository;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankAccountRepository;
import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.StatusEnum;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountRepository;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
import com.bkbwongo.core.ebaasa.walletmgt.utitlities.WalletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Service
public class AccountMappingServiceImp implements AccountMappingService{

    @Autowired
    private TWalletRepository walletRepository;

    @Autowired
    private TUserRepository userRepository;

    @Autowired
    private TSmsAccountRepository smsAccountRepository;

    @Autowired
    private TBankAccountRepository bankAccountRepository;

    @Autowired
    private TAccountMappingRepository accountMappingRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public Optional<TAccountMapping> addAccountMapping(AccountMappingDto accountMappingDto) {

        accountMappingDto.validate();

        var user = userRepository.findById(accountMappingDto.getUserId());
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, accountMappingDto.getUserId()));

        TAccountMapping accountMapping = new TAccountMapping();
        accountMapping.setUser(user.get());
        auditService.stampAuditedEntity(accountMapping);

        return Optional.of(accountMappingRepository.save(accountMapping));
    }

    @Override
    public Optional<TAccountMapping> linkAccount(AccountLinkDto accountLinkDto) {

        accountLinkDto.validate();

        var accountMapping = accountMappingRepository.findById(accountLinkDto.getAccountMappingId());
        Validate.isPresent(accountMapping, String.format(ErrorMessageConstants.ACCOUNT_MAPPING_NOT_FOUND, accountLinkDto.getAccountMappingId()));

        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS:
                result = linkSmsAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            case BANK:
                result = linkBankAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            case WALLET:
                result = linkWalletAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessageConstants.PROVIDER_TYPE_NOT_SUPPORTED);
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TAccountMapping> unlinkAccount(AccountLinkDto accountLinkDto) {

        accountLinkDto.validate();

        var accountMapping = accountMappingRepository.findById(accountLinkDto.getAccountMappingId());
        Validate.isPresent(accountMapping, String.format(ErrorMessageConstants.ACCOUNT_MAPPING_NOT_FOUND, accountLinkDto.getAccountMappingId()));

        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS:
                result = unlinkSmsAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            case BANK:
                result = unlinkBankAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            case WALLET:
                result = unlinkWalletAccount(accountLinkDto.getEntityId(), accountMapping.get());
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessageConstants.PROVIDER_TYPE_NOT_SUPPORTED);
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TAccountMapping> getAccountMappingById(Long id) {

        Validate.notNull(id, "AccountMapping ID required");

        var accountMapping = accountMappingRepository.findById(id);
        Validate.isPresent(accountMapping, String.format(ErrorMessageConstants.ACCOUNT_MAPPING_NOT_FOUND, id));

        return accountMapping;
    }

    @Override
    public Optional<TAccountMapping> getAccountMappingByUserId(Long id) {
        Validate.notNull(id, "user ID required");

        var user = userRepository.findById(id);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, id));

        var accountMapping = accountMappingRepository.findByUser(user.get());
        Validate.isPresent(accountMapping, String.format("Account mapping with user ID %s not found", id));

        return accountMapping;
    }

    private TAccountMapping linkWalletAccount(Long walletId, TAccountMapping accountMapping){

        var wallet = walletRepository.findById(walletId);
        Validate.isPresent(wallet, String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId));

        var am = accountMappingRepository.findByWallet(wallet.get());
        Validate.isPresent(am, "Wallet account is already mapped to user");

        WalletUtilities.checkThatAccountCanBeAssigned(wallet.get());
        setActivated(wallet.get());
        walletRepository.save(wallet.get());

        accountMapping.setWallet(wallet.get());
        accountMapping.setStatus(StatusEnum.ACTIVE);

        auditService.stampAuditedEntity(accountMapping);
        return accountMappingRepository.save(accountMapping);
    }

    private TAccountMapping unlinkWalletAccount(Long walletId, TAccountMapping accountMapping){
        return null;
    }

    private TAccountMapping linkBankAccount(Long bankAccountId, TAccountMapping accountMapping){

        var bankAccount = bankAccountRepository.findById(bankAccountId);
        Validate.isPresent(bankAccount, String.format(ErrorMessageConstants.BANK_ACCOUNT_NOT_FOUND, bankAccountId));

        var am = accountMappingRepository.findByBankAccount(bankAccount.get());
        Validate.isPresent(am, "Bank account is already mapped to user");

        return null;
    }

    private TAccountMapping unlinkBankAccount(Long bankAccountId, TAccountMapping accountMapping){
        return null;
    }

    private TAccountMapping linkSmsAccount(Long smsAccountId, TAccountMapping accountMapping){
        return null;
    }

    private TAccountMapping unlinkSmsAccount(Long smsAccountId, TAccountMapping accountMapping){
        return null;
    }

    private void setActivated(TWallet wallet){
        EbaasaLoginUser user = auditService.getLoggedInUser();

        final TUser s = new TUser();
        s.setId(user.getId());
        wallet.setActivatedBy(s);
        wallet.setActivateOn(DateTimeUtil.getCurrentUTCTime());
        wallet.setAssigned(Boolean.TRUE);
        wallet.setAccountStatus(AccountStatusEnum.ACTIVE);
        wallet.setStatusDescription(AccountStatusEnum.ACTIVE.getDescription());

    }

}
