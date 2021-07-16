package com.bkbwongo.core.ebaasa.smsmgt.service;

import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountGroupDto;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccountGroup;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 15/07/2021
 * @project ebaasa-sms
 */
public interface SmsAccountGroupService {
    Optional<TSmsAccountGroup> addSmsAccountGroup(SmsAccountGroupDto smsAccountGroupDto);
    Optional<TSmsAccountGroup> updateSmsAccountGroup(SmsAccountGroupDto smsAccountGroupDto);
    Boolean deleteSmsAccountGroup(Long id);
    Optional<TSmsAccountGroup> getSmsAccountGroupById(Long id);
    List<TSmsAccountGroup> getAllSmsAccountGroup();
}
