package com.bkbwongo.core.ebaasa.base.repository;

import com.bkbwongo.core.ebaasa.base.jpa.models.TCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TCountryRepository extends JpaRepository<TCountry, Long> {
    Optional<TCountry> findByIsoAlpha2(String isoAlpha2);
    Optional<TCountry> findByIsoAlpha3(String isoAlpha3);
}
