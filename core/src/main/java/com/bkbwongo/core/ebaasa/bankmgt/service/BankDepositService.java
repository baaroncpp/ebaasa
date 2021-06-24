package com.bkbwongo.core.ebaasa.bankmgt.service;

import com.bkbwongo.core.ebaasa.bankmgt.dto.BankDepositDto;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDeposit;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
public interface BankDepositService {
    Optional<TBankDeposit> initialDeposit(BankDepositDto bankDepositDto);
    Boolean firstDepositApproval(Long id, TUser approveUser);
    Boolean secondDepositApproval(Long id, TUser approveUser);
    Optional<TBankDeposit> getDepositById(Long id);
    List<TBankDeposit> getBankDepositsByBank(Long bankId, Pageable pageable);
    List<TBankDeposit> getBankDeposits(Pageable pageable);
}
