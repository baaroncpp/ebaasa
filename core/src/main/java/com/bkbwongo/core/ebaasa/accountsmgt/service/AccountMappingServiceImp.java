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
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountRepository;
import com.bkbwongo.core.ebaasa.smsmgt.utilities.SmsUtilities;
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
        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS_ACCOUNT:
                result = linkSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case WALLET:
                result = linkWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessageConstants.PROVIDER_TYPE_NOT_SUPPORTED);
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TAccountMapping> unlinkAccount(AccountLinkDto accountLinkDto) {

        accountLinkDto.validate();

        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS_ACCOUNT:
                result = unlinkSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case WALLET:
                result = unlinkWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessageConstants.PROVIDER_TYPE_NOT_SUPPORTED);
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TAccountMapping> linkSystemAccount(AccountLinkDto accountLinkDto) {
        accountLinkDto.validate();
        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS_ACCOUNT:
                result = linkMainSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case BANK:
                result = linkMainBankAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case WALLET:
                result = linkMainWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            default:
                throw new UnsupportedOperationException(ErrorMessageConstants.PROVIDER_TYPE_NOT_SUPPORTED);
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TAccountMapping> unlinkSystemAccount(AccountLinkDto accountLinkDto) {
        accountLinkDto.validate();
        TAccountMapping result = null;

        switch (accountLinkDto.getLinkType()){
            case SMS_ACCOUNT:
                result = unlinkMainSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case BANK:
                result = unlinkMainBankAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
                break;
            case WALLET:
                result = unlinkMainWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
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

    private TAccountMapping linkWalletAccount(Long walletId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var wallet = walletRepository.findById(walletId);
        Validate.isPresent(wallet, String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId));

        var walletMappingToCheck = accountMappingRepository.findByWallet(wallet.get());
        Validate.isTrue(!walletMappingToCheck.isPresent(), "User already has a wallet account");

        var am = accountMappingRepository.findByUser(user.get());
        TAccountMapping accountMapping = null;
        if (am.isPresent()){
            accountMapping = am.get();
        }else {
            accountMapping = new TAccountMapping();
        }

        WalletUtilities.checkThatAccountCanBeAssigned(wallet.get());
        setActivated(wallet.get());
        walletRepository.save(wallet.get());

        accountMapping.setWallet(wallet.get());
        accountMapping.setStatus(StatusEnum.ACTIVE);

        auditService.stampAuditedEntity(accountMapping);
        return accountMappingRepository.save(accountMapping);
    }

    private TAccountMapping unlinkWalletAccount(Long walletId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var walletToUnlink = walletRepository.findById(walletId);
        Validate.isPresent(walletToUnlink, String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId));

        final var wallet = walletToUnlink.get();

        var am = accountMappingRepository.findByWallet(wallet);
        Validate.isPresent(am, "Wallet is not linked to Account Mapping");
        Validate.isTrue(am.get().getUser().getId().equals(userId), String.format(ErrorMessageConstants.WALLET_MAPPING_FOR_USER_DOES_NOT_EXIST, walletId, userId));

        WalletUtilities.checkThatAccountCanBeUnAssigned(wallet);

        wallet.setAccountStatus(AccountStatusEnum.CLOSED);
        wallet.setStatusDescription(AccountStatusEnum.CLOSED.getDescription());
        wallet.setAssigned(Boolean.FALSE);

        auditService.stampAuditedEntity(wallet);
        walletRepository.save(wallet);

        final TAccountMapping mapping = am.get();
        mapping.setStatus(StatusEnum.NOT_ACTIVE);
        auditService.stampAuditedEntity(mapping);

        return accountMappingRepository.save(mapping);
    }

    private TAccountMapping linkMainBankAccount(Long bankAccountId, Long userId){

        var bankAccount = bankAccountRepository.findById(bankAccountId);
        Validate.isPresent(bankAccount, String.format(ErrorMessageConstants.BANK_ACCOUNT_NOT_FOUND, bankAccountId));

        var am = accountMappingRepository.findByBankAccount(bankAccount.get());
        Validate.isTrue(am.isPresent(), "Bank account is already mapped to user");

        return null;
    }

    private TAccountMapping linkMainSmsAccount(Long bankAccountId, Long userId){
        return null;
    }

    private TAccountMapping linkMainWalletAccount(Long walletId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var wallet = walletRepository.findById(walletId);
        Validate.isPresent(wallet, String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId));

        var walletMappingToCheck = accountMappingRepository.findByWallet(wallet.get());
        Validate.isTrue(!walletMappingToCheck.isPresent(), "User already has a wallet account");

        var am = accountMappingRepository.findByUser(user.get());
        TAccountMapping accountMapping = null;
        if (am.isPresent()){
            accountMapping = am.get();
        }else {
            accountMapping = new TAccountMapping();
        }

        WalletUtilities.checkThatAccountCanBeAssignedAsMain(wallet.get());
        setActivated(wallet.get());
        walletRepository.save(wallet.get());

        accountMapping.setWallet(wallet.get());
        accountMapping.setStatus(StatusEnum.ACTIVE);

        auditService.stampAuditedEntity(accountMapping);
        return accountMappingRepository.save(accountMapping);
    }

    private TAccountMapping unlinkMainSmsAccount(Long bankAccountId, Long userId){
        return null;
    }

    private TAccountMapping unlinkMainWalletAccount(Long bankAccountId, Long userId){
        return null;
    }

    private TAccountMapping unlinkMainBankAccount(Long bankAccountId, Long userId){
        return null;
    }

    private TAccountMapping linkSmsAccount(Long smsAccountId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var smsAccount = smsAccountRepository.findById(smsAccountId);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, smsAccountId));

        var accountMapping = accountMappingRepository.findBySmsAccount(smsAccount.get());
        Validate.isTrue(!accountMapping.isPresent(), "User already has sms account");

        var mapping = accountMappingRepository.findByUser(user.get());
        TAccountMapping result = null;
        if (mapping.isPresent()){
            result = mapping.get();
        }else {
            result = new TAccountMapping();
            result.setStatus(StatusEnum.ACTIVE);
        }

        SmsUtilities.checkThatAccountCanBeAssigned(smsAccount.get());
        setSmsAccountActivated(smsAccount.get());

        smsAccountRepository.save(smsAccount.get());

        result.setSmsAccount(smsAccount.get());

        return accountMappingRepository.save(result);
    }

    private TAccountMapping unlinkSmsAccount(Long smsAccountId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var smsAccount = smsAccountRepository.findById(smsAccountId);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, smsAccountId));

        var accountMapping = accountMappingRepository.findBySmsAccount(smsAccount.get());
        Validate.isTrue(accountMapping.get().getUser().getId().equals(userId), "Sms account is not mapped to user");

        SmsUtilities.checkThatAccountCanBeUnAssigned(smsAccount.get());

        smsAccount.get().setAccountStatus(AccountStatusEnum.CLOSED);
        smsAccount.get().setStatusDescription(AccountStatusEnum.CLOSED.getDescription());
        smsAccount.get().setAssigned(Boolean.FALSE);

        auditService.stampAuditedEntity(smsAccount.get());
        smsAccountRepository.save(smsAccount.get());

        final TAccountMapping mapping = accountMapping.get();
        mapping.setStatus(StatusEnum.NOT_ACTIVE);
        auditService.stampAuditedEntity(mapping);

        return accountMappingRepository.save(mapping);
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

    private void setSmsAccountActivated(TSmsAccount smsAccount){
        EbaasaLoginUser user = auditService.getLoggedInUser();

        final TUser s = new TUser();
        s.setId(user.getId());
        smsAccount.setActivatedBy(s);
        smsAccount.setActivatedOn(DateTimeUtil.getCurrentUTCTime());
        smsAccount.setAssigned(Boolean.TRUE);
        smsAccount.setAccountStatus(AccountStatusEnum.ACTIVE);
        smsAccount.setStatusDescription(AccountStatusEnum.ACTIVE.getDescription());

    }

}
