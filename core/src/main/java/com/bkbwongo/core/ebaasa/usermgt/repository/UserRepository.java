package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
@Repository
public interface UserRepository extends JpaRepository<String, TUser> {
    Optional<TUser> findByUsername(String username);
}
