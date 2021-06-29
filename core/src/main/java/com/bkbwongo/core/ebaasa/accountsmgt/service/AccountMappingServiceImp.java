package com.bkbwongo.core.ebaasa.accountsmgt.service;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountLinkDto;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountMappingDto;
import com.bkbwongo.core.ebaasa.accountsmgt.jpa.models.TAccountMapping;
import com.bkbwongo.core.ebaasa.accountsmgt.repository.TAccountMappingRepository;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankAccountRepository;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
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
                result = linkSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
                break;
            case BANK:
                result = linkBankAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
                break;
            case WALLET:
                result = linkWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
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
                result = unlinkSmsAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
                break;
            case BANK:
                result = unlinkBankAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
                break;
            case WALLET:
                result = unlinkWalletAccount(accountLinkDto.getEntityId(), accountLinkDto.getAccountMappingId());
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

    private TAccountMapping linkWalletAccount(Long walletId, Long accountMappingId){
        return null;
    }

    private TAccountMapping unlinkWalletAccount(Long walletId, Long accountMappingId){
        return null;
    }

    private TAccountMapping linkBankAccount(Long bankAccountId, Long accountMappingId){
        return null;
    }

    private TAccountMapping unlinkBankAccount(Long bankAccountId, Long accountMappingId){
        return null;
    }

    private TAccountMapping linkSmsAccount(Long smsAccountId, Long accountMappingId){
        return null;
    }

    private TAccountMapping unlinkSmsAccount(Long smsAccountId, Long accountMappingId){
        return null;
    }
}
