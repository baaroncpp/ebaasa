package com.bkbwongo.core.ebaasa.smsmgt.service;

import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsPackageTypeDto;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsPackageType;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 11/07/2021
 * @project ebaasa-sms
 */
public interface SmsPackageTypeService {
    Optional<TSmsPackageType> createSmsPackageType(SmsPackageTypeDto smsPackageTypeDto);
    Optional<TSmsPackageType> updateSmsPackageType(SmsPackageTypeDto smsPackageTypeDto);
    Optional<TSmsPackageType> getSmsPackageTypeBy(Long id);
    Optional<TSmsPackageType> activateSmsPackageType(Long id);
    Optional<TSmsPackageType> closeSmsPackageType(Long id);
    List<TSmsPackageType> getSmsPackageTypes();
}
