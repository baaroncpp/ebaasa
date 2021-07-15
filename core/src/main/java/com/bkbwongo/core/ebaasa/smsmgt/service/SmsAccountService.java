package com.bkbwongo.core.ebaasa.smsmgt.service;

import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountDto;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/07/2021
 * @project ebaasa-sms
 */
public interface SmsAccountService {
    Optional<TSmsAccount> addSmsAccount(SmsAccountDto smsAccountDto);
    Optional<TSmsAccount> updateSmsAccount(SmsAccountDto smsAccountDto);
    Optional<TSmsAccount> getSmsAccountBy(Long id);
    Optional<TSmsAccount> closeSmsAccount(Long id);
    Optional<TSmsAccount> activateSmsAccount(Long id);
    Optional<TSmsAccount> suspendSmsAccount(Long id);
    List<TSmsAccount> getSmsAccounts(Pageable pageable);
}
