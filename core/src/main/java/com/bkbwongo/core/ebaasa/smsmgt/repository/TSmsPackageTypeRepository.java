package com.bkbwongo.core.ebaasa.smsmgt.repository;

import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsPackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 04/07/2021
 * @project ebaasa-sms
 */
@Repository
public interface TSmsPackageTypeRepository extends JpaRepository<TSmsPackageType, Long> {
    Optional<TSmsPackageType> findByName(String name);
}
