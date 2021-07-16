package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccountGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/07/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsAccountGroupRepository extends JpaRepository<TSmsAccountGroup, Long> {
    Optional<TSmsAccountGroup> findByName(String name);
    Optional<TSmsAccountGroup> findBySmsDebitLimit(Long id);
}
