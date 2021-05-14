package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TRoleRepository extends JpaRepository<TRole, Long> {
    Optional<TRole> findByName(String name);
}
