package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsAccountRepository extends JpaRepository<TSmsAccount, Long> {
}
