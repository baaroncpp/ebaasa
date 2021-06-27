package com.bkbwongo.core.ebaasa.security.repository;

import com.bkbwongo.core.ebaasa.security.jpa.model.TAppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 26/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TAppClientRepository extends JpaRepository<TAppClient, Long> {
    Optional<TAppClient> getByName(String name);
}
