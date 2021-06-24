package com.bkbwongo.core.ebaasa.bankmgt.repository;

import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 22/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TBankDepositRepository extends JpaRepository<TBankDeposit, Long> {
}
