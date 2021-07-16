package com.bkbwongo.core.ebaasa.smsmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountGroupDto;
import com.bkbwongo.core.ebaasa.smsmgt.dto.service.SmsManagementDTOService;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccountGroup;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountGroupRepository;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsAccountRepository;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsAccountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 15/07/2021
 * @project ebaasa-sms
 */
@Service
public class SmsAccountGroupServiceImp implements SmsAccountGroupService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private TSmsAccountGroupRepository smsAccountGroupRepository;

    @Autowired
    private SmsManagementDTOService smsManagementDTOService;

    @Autowired
    private TSmsAccountRepository smsAccountRepository;

    @Override
    public Optional<TSmsAccountGroup> addSmsAccountGroup(SmsAccountGroupDto smsAccountGroupDto) {

        smsAccountGroupDto.validate();

        var smsAccountGroupName = smsAccountGroupRepository.findByName(smsAccountGroupDto.getName());
        Validate.isPresent(smsAccountGroupName, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_WITH_NAME_EXISTS, smsAccountGroupDto.getName()));

        var smsAccountGroupSmsLimit = smsAccountGroupRepository.findBySmsDebitLimit(smsAccountGroupDto.getSmsDebitLimit());
        Validate.isPresent(smsAccountGroupSmsLimit, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_WITH_SMS_LIMIT_EXISTS, smsAccountGroupDto.getSmsDebitLimit()));

        var smsAccountGroupToAdd = smsManagementDTOService.convertDTOToTSmsAccountGroup(smsAccountGroupDto);
        auditService.stampAuditedEntity(smsAccountGroupToAdd);

        return Optional.of(smsAccountGroupRepository.save(smsAccountGroupToAdd));
    }

    @Override
    public Optional<TSmsAccountGroup> updateSmsAccountGroup(SmsAccountGroupDto smsAccountGroupDto) {

        smsAccountGroupDto.validate();

        var smsAccountGroup = smsAccountGroupRepository.findById(smsAccountGroupDto.getId());
        Validate.isPresent(smsAccountGroup, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_NOT_FOUND, smsAccountGroupDto.getId()));

        var smsAccountGroupToAdd = smsManagementDTOService.convertDTOToTSmsAccountGroup(smsAccountGroupDto);
        auditService.stampAuditedEntity(smsAccountGroupToAdd);

        return Optional.of(smsAccountGroupRepository.save(smsAccountGroupToAdd));
    }

    @Override
    public Boolean deleteSmsAccountGroup(Long id) {

        Validate.notNull(id, "ID not defined");
        var smsAccountGroup = smsAccountGroupRepository.findById(id);
        Validate.isPresent(smsAccountGroup, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_NOT_FOUND, id));

        List<TSmsAccount> smsAccounts = smsAccountRepository.findAllBySmsAccountType(smsAccountGroup.get());
        Validate.isTrue(!smsAccounts.isEmpty(), "Sms accounts attached to account group, cannot be deleted");

        smsAccountGroupRepository.delete(smsAccountGroup.get());

        var smsAccountGroupDeleted = smsAccountGroupRepository.findById(id);
        if (smsAccountGroupDeleted.isPresent())
            return false;

        return true;
    }

    @Override
    public Optional<TSmsAccountGroup> getSmsAccountGroupById(Long id) {

        Validate.notNull(id, "ID not defined");
        var smsAccountGroup = smsAccountGroupRepository.findById(id);
        Validate.isPresent(smsAccountGroup, String.format(ErrorMessageConstants.SMS_ACCOUNT_GROUP_NOT_FOUND, id));

        return smsAccountGroup;
    }

    @Override
    public List<TSmsAccountGroup> getAllSmsAccountGroup() {

        List<TSmsAccountGroup> smsAccountGroups = smsAccountGroupRepository.findAll();
        Validate.notNull(smsAccountGroups, "No account groups in the system");

        return smsAccountGroups;
    }
}
