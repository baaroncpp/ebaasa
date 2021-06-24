package com.bkbwongo.core.ebaasa.bankmgt.service;

import com.bkbwongo.core.ebaasa.bankmgt.dto.BankAccountDto;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
public interface BankAccountService {
    Optional<TBankAccount> addBankAccount(BankAccountDto bankAccountDto);
    Optional<TBankAccount> updateBankAccount(BankAccountDto bankAccountDto);
    Optional<TBankAccount> getBankAccountById(Long id);
    List<TBankAccount> getAllBankAccounts();
}
