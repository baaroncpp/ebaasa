package com.bkbwongo.core.ebaasa.walletmgt.repository;

import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TCashFlowRepository extends JpaRepository<TCashFlow, Long> {
    Page<TCashFlow> findByFromWalletTransaction(TWallet wallet, Pageable pageable);
    Page<TCashFlow> findByToWalletTransaction(TWallet wallet, Pageable pageable);
    Page<TCashFlow> findAll(Pageable pageable);
}
