package com.bkbwongo.core.ebaasa.repository;

import com.bkbwongo.core.ebaasa.jpa.models.TAppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 04/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TAppClientRepository extends JpaRepository<TAppClient, Long> {
}
