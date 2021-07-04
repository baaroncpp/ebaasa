package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 04/07/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsRepository extends JpaRepository<TSms, Long> {
}
