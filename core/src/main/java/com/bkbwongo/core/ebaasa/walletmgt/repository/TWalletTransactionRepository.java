package com.bkbwongo.core.ebaasa.walletmgt.repository;

import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TWalletTransactionRepository extends JpaRepository<TWalletTransaction, Long> {
    Page<TWalletTransaction> findByWallet(TWallet wallet, Pageable pageable);
}
