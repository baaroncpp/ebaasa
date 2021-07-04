package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 04/07/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsTransactionRepository extends JpaRepository<TSmsTransaction, Long> {
}
