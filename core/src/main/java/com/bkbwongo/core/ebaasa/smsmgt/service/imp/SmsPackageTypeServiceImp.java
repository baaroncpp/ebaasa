package com.bkbwongo.core.ebaasa.smsmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsPackageTypeDto;
import com.bkbwongo.core.ebaasa.smsmgt.dto.service.SmsManagementDTOService;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsPackageType;
import com.bkbwongo.core.ebaasa.smsmgt.repository.TSmsPackageTypeRepository;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsPackageTypeService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 11/07/2021
 * @project ebaasa-sms
 */
@Service
public class SmsPackageTypeServiceImp implements SmsPackageTypeService {

    @Autowired
    private TSmsPackageTypeRepository smsPackageTypeRepository;

    @Autowired
    private AuditService auditService;

    private SmsManagementDTOService smsManagementDTOService;

    @Override
    public Optional<TSmsPackageType> createSmsPackageType(SmsPackageTypeDto smsPackageTypeDto) {

        smsPackageTypeDto.validate();

        var packageType = smsPackageTypeRepository.findByName(smsPackageTypeDto.getName());
        Validate.isPresent(packageType, String.format(ErrorMessageConstants.SMS_PACKAGE_TYPE_NOT_FOUND, smsPackageTypeDto.getName()));

        var smsPackageType = smsManagementDTOService.convertDTOToTSmsPackageType(smsPackageTypeDto);

        auditService.stampAuditedEntity(smsPackageType);
        return Optional.of(smsPackageTypeRepository.save(smsPackageType));
    }

    @Override
    public Optional<TSmsPackageType> updateSmsPackageType(SmsPackageTypeDto smsPackageTypeDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TSmsPackageType> getSmsPackageTypeBy(Long id) {

        Validate.notNull(id, "ID not defined");
        var smsPackageType = smsPackageTypeRepository.findById(id);
        Validate.isPresent(smsPackageType, String.format(ErrorMessageConstants.SMS_PACKAGE_TYPE_NOT_FOUND, id));

        return smsPackageType;
    }

    @Override
    public Optional<TSmsPackageType> activateSmsPackageType(Long id) {

        Validate.notNull(id, "ID not defined");
        var smsPackageType = smsPackageTypeRepository.findById(id);
        Validate.isPresent(smsPackageType, String.format(ErrorMessageConstants.SMS_PACKAGE_TYPE_NOT_FOUND, id));

        var activationEntity = smsPackageType.get();
        Validate.isTrue(activationEntity.getActive().equals(Boolean.TRUE),ErrorMessageConstants.SMS_PACKAGE_TYPE_ALREADY_ACTIVE);

        auditService.stampAuditedEntity(activationEntity);
        TUser activatingUser = activationEntity.getModifiedBy();

        activationEntity.setActive(Boolean.TRUE);
        activationEntity.setActivatedBy(activatingUser);

        return Optional.of(smsPackageTypeRepository.save(activationEntity));
    }

    @Override
    public Optional<TSmsPackageType> closeSmsPackageType(Long id) {

        Validate.notNull(id, "ID not defined");
        var smsPackageType = smsPackageTypeRepository.findById(id);
        Validate.isPresent(smsPackageType, String.format(ErrorMessageConstants.SMS_PACKAGE_TYPE_NOT_FOUND, id));

        var activationEntity = smsPackageType.get();
        Validate.isTrue((activationEntity.getClosed().equals(Boolean.TRUE)), ErrorMessageConstants.SMS_PACKAGE_TYPE_ALREADY_CLOSED);

        auditService.stampAuditedEntity(activationEntity);
        TUser closingUser = activationEntity.getModifiedBy();

        activationEntity.setClosedBy(closingUser);
        activationEntity.setClosed(Boolean.TRUE);

        return Optional.of(smsPackageTypeRepository.save(activationEntity));
    }

    @Override
    public List<TSmsPackageType> getSmsPackageTypes() {
        return smsPackageTypeRepository.findAll();
    }
}
