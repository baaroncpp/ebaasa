package com.bkbwongo.core.ebaasa.smsmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountDto;
import com.bkbwongo.core.ebaasa.smsmgt.dto.service.SmsManagementDTOService;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountGroupRepository;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountRepository;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsAccountService;
import com.bkbwongo.core.ebaasa.smsmgt.utilities.SmsUtilities;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/07/2021
 * @project ebaasa-sms
 */
@Service
public class SmsAccountServiceImp implements SmsAccountService {

    @Autowired
    private TSmsAccountRepository smsAccountRepository;

    @Autowired
    private TSmsAccountGroupRepository smsAccountGroupRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private SmsManagementDTOService smsManagementDTOService;

    @Override
    public Optional<TSmsAccount> addSmsAccount(SmsAccountDto smsAccountDto) {

        smsAccountDto.validate();

        var sa = smsAccountRepository.findByName(smsAccountDto.getName());
        Validate.isPresent(sa, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, smsAccountDto.getName()));

        var smsAccount = smsManagementDTOService.convertDTOToTSmsAccount(smsAccountDto);

        var smsAccountGroup = smsAccountGroupRepository.findById(smsAccount.getSmsAccountType().getId());
        Validate.isPresent(smsAccountGroup, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_NOT_FOUND, smsAccount.getSmsAccountType().getId()));

        auditService.stampAuditedEntity(smsAccount);
        return Optional.of(smsAccountRepository.save(smsAccount));
    }

    @Override
    public Optional<TSmsAccount> updateSmsAccount(SmsAccountDto smsAccountDto) {

        smsAccountDto.validate();

        var smsAccount = smsAccountRepository.findById(smsAccountDto.getId());
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, smsAccountDto.getId()));

        var smsAccountToUpdate = smsManagementDTOService.convertDTOToTSmsAccount(smsAccountDto);
        auditService.stampAuditedEntity(smsAccountToUpdate);

        return Optional.of(smsAccountRepository.save(smsAccountToUpdate));
    }

    @Override
    public Optional<TSmsAccount> getSmsAccountBy(Long id) {

        Validate.notNull(id, "ID value not defined");
        var smsAccount = smsAccountRepository.findById(id);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, id));

        return smsAccount;
    }

    @Override
    public Optional<TSmsAccount> closeSmsAccount(Long id) {

        Validate.notNull(id, "ID value not defined");
        var smsAccount = smsAccountRepository.findById(id);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, id));

        Validate.isTrue(smsAccount.get().getClosed().equals(Boolean.TRUE), ErrorMessageConstants.SMS_ACCOUNT_ALREADY_CLOSED);

        var smsAccountToClose = smsAccount.get();
        SmsUtilities.checkThatAccountCanBeUnAssigned(smsAccountToClose);

        auditService.stampAuditedEntity(smsAccountToClose);
        TUser closingUser = smsAccountToClose.getModifiedBy();

        smsAccountToClose.setClosed(Boolean.TRUE);
        smsAccountToClose.setClosedBy(closingUser);
        smsAccountToClose.setActive(Boolean.FALSE);
        smsAccountToClose.setAssigned(Boolean.FALSE);

        return Optional.of(smsAccountRepository.save(smsAccountToClose));
    }

    @Override
    public Optional<TSmsAccount> activateSmsAccount(Long id) {

        Validate.notNull(id, "ID value not defined");
        var smsAccount = smsAccountRepository.findById(id);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, id));

        Validate.isTrue(smsAccount.get().getClosed().equals(Boolean.TRUE), ErrorMessageConstants.SMS_ACCOUNT_ALREADY_CLOSED);
        Validate.isTrue(smsAccount.get().getActive().equals(Boolean.TRUE), ErrorMessageConstants.SMS_PACKAGE_TYPE_ALREADY_ACTIVE);

        var smsAccountToActivate = smsAccount.get();

        auditService.stampAuditedEntity(smsAccountToActivate);
        TUser activatingUser = smsAccountToActivate.getModifiedBy();

        smsAccountToActivate.setActive(Boolean.TRUE);
        smsAccountToActivate.setActivatedBy(activatingUser);
        smsAccountToActivate.setSuspended(Boolean.FALSE);

        auditService.stampAuditedEntity(smsAccountToActivate);

        return Optional.of(smsAccountRepository.save(smsAccountToActivate));
    }

    @Override
    public Optional<TSmsAccount> suspendSmsAccount(Long id) {

        Validate.notNull(id, "ID value not defined");
        var smsAccount = smsAccountRepository.findById(id);
        Validate.isPresent(smsAccount, String.format(ErrorMessageConstants.SMS_ACCOUNT_NOT_FOUND, id));

        Validate.isTrue(smsAccount.get().getClosed().equals(Boolean.TRUE), ErrorMessageConstants.SMS_ACCOUNT_ALREADY_CLOSED);
        Validate.isTrue(smsAccount.get().getSuspended().equals(Boolean.TRUE), ErrorMessageConstants.SMS_ACCOUNT_ALREADY_SUSPENDED);

        var smsAccountToSuspend = smsAccount.get();

        auditService.stampAuditedEntity(smsAccountToSuspend);
        TUser suspendingUser = smsAccountToSuspend.getModifiedBy();

        smsAccountToSuspend.setActive(Boolean.FALSE);
        smsAccountToSuspend.setSuspendedBy(suspendingUser);
        smsAccountToSuspend.setSuspended(Boolean.TRUE);

        return Optional.of(smsAccountRepository.save(smsAccountToSuspend));
    }

    @Override
    public List<TSmsAccount> getSmsAccounts(Pageable pageable) {
        List<TSmsAccount> smsAccounts = smsAccountRepository.findAll(pageable).getContent();
        Validate.notNull(smsAccounts, "No sms accounts present");

        return smsAccounts;
    }
}
