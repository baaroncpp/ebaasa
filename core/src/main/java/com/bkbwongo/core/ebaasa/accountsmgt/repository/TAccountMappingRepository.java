package com.bkbwongo.core.ebaasa.accountsmgt.repository;

import com.bkbwongo.core.ebaasa.accountsmgt.jpa.models.TAccountMapping;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TAccountMappingRepository extends JpaRepository<TAccountMapping, Long> {
    Optional<TAccountMapping> findByUser(TUser user);
    Optional<TAccountMapping> findByBankAccount(TBankAccount bankAccount);
    Optional<TAccountMapping> findByWallet(TWallet wallet);
    Optional<TAccountMapping> findBySmsAccount(TSmsAccount smsAccount);
}
