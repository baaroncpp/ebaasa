package com.bkbwongo.core.ebaasa.accountsmgt.service;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountLinkDto;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountMappingDto;
import com.bkbwongo.core.ebaasa.accountsmgt.jpa.models.TAccountMapping;
import com.bkbwongo.core.ebaasa.accountsmgt.repository.TAccountMappingRepository;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankAccountRepository;
import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.StatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.UserTypeEnum;
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

        TUser userToLink = user.get();

        var accountMapping = new TAccountMapping();
        accountMapping.setUser(userToLink);
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
            case WALLET:
                result = linkMainWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getUserId());
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
        Validate.isTrue(!user.get().getUserType().name().equals(UserTypeEnum.SYSTEM), "System account cannot be linked to this wallet");

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
            accountMapping.setStatus(StatusEnum.ACTIVE);
        }

        TWallet walletToLink = wallet.get();

        WalletUtilities.checkThatAccountCanBeAssigned(walletToLink);
        setActivated(walletToLink);
        auditService.stampAuditedEntity(walletToLink);

        walletRepository.save(walletToLink);

        accountMapping.setWallet(walletToLink);

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

    @Override
    public TAccountMapping linkMainBankAccountToWallet(Long bankAccountId, Long walletId){

        var bankAccount = bankAccountRepository.findById(bankAccountId);
        Validate.isPresent(bankAccount, String.format(ErrorMessageConstants.BANK_ACCOUNT_NOT_FOUND, bankAccountId));
        Validate.isTrue(bankAccount.get().getAssigned(), "bank is already assigned");

        var wallet = walletRepository.findById(walletId);
        Validate.isPresent(wallet, String.format(ErrorMessageConstants.SAME_WALLET_ACCOUNT));

        var am = accountMappingRepository.findByBankAccount(bankAccount.get());
        Validate.isTrue(!am.isPresent(), "Bank account is already mapped to a wallet account");

        final TBankAccount bankToAssign = bankAccount.get();
        bankToAssign.setAssigned(Boolean.TRUE);
        final TWallet walletToAssign = wallet.get();

        WalletUtilities.checkThatAccountCanBeAssignedAsMain(walletToAssign);
        setActivated(walletToAssign);
        auditService.stampAuditedEntity(walletToAssign);

        bankAccountRepository.save(bankToAssign);
        walletRepository.save(walletToAssign);

        var accountMapping = new TAccountMapping();
        accountMapping.setBankAccount(bankToAssign);
        accountMapping.setWallet(walletToAssign);
        accountMapping.setStatus(StatusEnum.ACTIVE);
        auditService.stampAuditedEntity(accountMapping);

        return accountMappingRepository.save(accountMapping);
    }

    @Override
    public TAccountMapping unlinkMainBankAccountToWallet(Long bankAccountId, Long walletId) {

        var bankAccount = bankAccountRepository.findById(bankAccountId);
        Validate.isPresent(bankAccount, String.format(ErrorMessageConstants.BANK_ACCOUNT_NOT_FOUND, bankAccountId));

        var walletToUnlink = walletRepository.findById(walletId);

        Optional<TAccountMapping> accountMapping = accountMappingRepository.findByBankAccount(bankAccount.get());
        Validate.isPresent(accountMapping,"Bank is not linked to this wallet account");
        Validate.isTrue(accountMapping.get().getWallet().getId().equals(walletId), String.format(ErrorMessageConstants.WALLET_MAPPING_TO_BANK_DOES_NOT_EXIST, walletId, bankAccountId));

        final TWallet wallet = walletToUnlink.get();
        final TBankAccount bank = bankAccount.get();
        bank.setAssigned(Boolean.FALSE);
        auditService.stampLongEntity(bank);

        bankAccountRepository.save(bank);

        WalletUtilities.checkThatAccountCanBeUnAssigned(wallet);

        wallet.setAccountStatus(AccountStatusEnum.CLOSED);
        wallet.setStatusDescription(AccountStatusEnum.CLOSED.getDescription());
        wallet.setAssigned(Boolean.FALSE);

        auditService.stampAuditedEntity(wallet);
        walletRepository.save(wallet);

        final TAccountMapping mapping = accountMapping.get();
        mapping.setStatus(StatusEnum.NOT_ACTIVE);
        auditService.stampAuditedEntity(mapping);

        return accountMappingRepository.save(mapping);
    }

    private TAccountMapping linkMainSmsAccount(Long smsAccountId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));
        Validate.isTrue(user.get().getUserType().name().equals(UserTypeEnum.SYSTEM.name()), ErrorMessageConstants.ONLY_SYSTEM_ACCOUNT_TO_MAIN_ACCOUNT);

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

        TSmsAccount smsAccountToLink = smsAccount.get();

        SmsUtilities.checkThatAccountCanBeAssignedAsMain(smsAccountToLink);
        setSmsAccountActivated(smsAccountToLink);
        auditService.stampAuditedEntity(smsAccountToLink);

        smsAccountRepository.save(smsAccountToLink);

        result.setSmsAccount(smsAccountToLink);
        auditService.stampAuditedEntity(result);

        return accountMappingRepository.save(result);
    }

    private TAccountMapping linkMainWalletAccount(Long walletId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));
        Validate.isTrue(user.get().getUserType().name().equals(UserTypeEnum.SYSTEM.name()), ErrorMessageConstants.ONLY_SYSTEM_ACCOUNT_TO_MAIN_ACCOUNT);

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
            accountMapping.setStatus(StatusEnum.ACTIVE);
        }

        TWallet walletToAssign = wallet.get();

        WalletUtilities.checkThatAccountCanBeAssignedAsMain(walletToAssign);
        setActivated(walletToAssign);
        auditService.stampAuditedEntity(walletToAssign);

        walletRepository.save(walletToAssign);

        accountMapping.setWallet(walletToAssign);
        auditService.stampAuditedEntity(accountMapping);

        return accountMappingRepository.save(accountMapping);
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

        final TSmsAccount smsAccountToLink = smsAccount.get();

        SmsUtilities.checkThatAccountCanBeAssigned(smsAccountToLink);
        setSmsAccountActivated(smsAccountToLink);
        auditService.stampAuditedEntity(smsAccountToLink);

        smsAccountRepository.save(smsAccountToLink);

        result.setSmsAccount(smsAccountToLink);
        auditService.stampAuditedEntity(result);

        return accountMappingRepository.save(result);
    }

    private TAccountMapping unlinkSmsAccount(Long smsAccountId, Long userId){

        var user = userRepository.findById(userId);
        Validate.isPresent(user, String.format(ErrorMessageConstants.ID_NOT_FOUND, userId));

        var smsAccount = smsAccountRepository.findById(smsAccountId);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, smsAccountId));

        var accountMapping = accountMappingRepository.findBySmsAccount(smsAccount.get());
        Validate.isTrue(accountMapping.get().getUser().getId().equals(userId), "Sms account is not mapped to user");

        TSmsAccount smsAccountToLink = smsAccount.get();

        SmsUtilities.checkThatAccountCanBeUnAssigned(smsAccountToLink);

        smsAccountToLink.setAccountStatus(AccountStatusEnum.CLOSED);
        smsAccountToLink.setStatusDescription(AccountStatusEnum.CLOSED.getDescription());
        smsAccountToLink.setAssigned(Boolean.FALSE);

        auditService.stampAuditedEntity(smsAccountToLink);
        smsAccountRepository.save(smsAccountToLink);

        final TAccountMapping mapping = accountMapping.get();
        mapping.setStatus(StatusEnum.NOT_ACTIVE);
        auditService.stampAuditedEntity(mapping);

        return accountMappingRepository.save(mapping);
    }

    private void setActivated(TWallet wallet){
        EbaasaLoginUser user = auditService.getLoggedInUser();

        final var s = new TUser();
        s.setId(user.getId());
        wallet.setActivatedBy(s);
        wallet.setActivateOn(DateTimeUtil.getCurrentUTCTime());
        wallet.setAssigned(Boolean.TRUE);
        wallet.setAccountStatus(AccountStatusEnum.ACTIVE);
        wallet.setStatusDescription(AccountStatusEnum.ACTIVE.getDescription());

    }

    private void setSmsAccountActivated(TSmsAccount smsAccount){
        EbaasaLoginUser user = auditService.getLoggedInUser();

        final var s = new TUser();
        s.setId(user.getId());
        smsAccount.setActivatedBy(s);
        smsAccount.setActivatedOn(DateTimeUtil.getCurrentUTCTime());
        smsAccount.setAssigned(Boolean.TRUE);
        smsAccount.setAccountStatus(AccountStatusEnum.ACTIVE);
        smsAccount.setStatusDescription(AccountStatusEnum.ACTIVE.getDescription());

    }

}
