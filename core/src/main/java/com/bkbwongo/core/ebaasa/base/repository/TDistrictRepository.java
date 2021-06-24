package com.bkbwongo.core.ebaasa.base.repository;

import com.bkbwongo.core.ebaasa.base.jpa.models.TDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TDistrictRepository extends JpaRepository<TDistrict, Long> {
}
