package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 13/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TUserGroupRepository extends JpaRepository<TUserGroup, Long> {
    Optional<TUserGroup> findByName(String name);
}
