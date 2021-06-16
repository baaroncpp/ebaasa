package com.bkbwongo.core.ebaasa.walletmgt.repository;

import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TCashFlowRequestRepository extends JpaRepository<TCashFlow, Long> {
}
