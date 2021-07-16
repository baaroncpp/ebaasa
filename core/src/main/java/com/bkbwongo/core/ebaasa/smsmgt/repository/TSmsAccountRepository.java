package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccountGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsAccountRepository extends JpaRepository<TSmsAccount, Long> {
    Optional<TSmsAccount> findByName(String name);
    Page<TSmsAccount> findAll(Pageable pageable);
    List<TSmsAccount> findAllBySmsAccountType(TSmsAccountGroup smsAccountGroup);
}
