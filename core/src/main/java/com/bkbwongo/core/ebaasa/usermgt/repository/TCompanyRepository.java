package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TCompany;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TCompanyRepository extends JpaRepository<TCompany, Long> {
    Optional<TCompany> findByBusinessName(String businessName);
    Optional<TCompany> findByCreatedBy(TUser createdBy);
    Page<TCompany> findAll(Pageable pageable);
}
