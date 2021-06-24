package com.bkbwongo.core.ebaasa.bankmgt.repository;

import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TBankAccountRepository extends JpaRepository<TBankAccount, Long> {
    Optional<TBankAccount> findByAccountNumber(String accountNumber);
}
