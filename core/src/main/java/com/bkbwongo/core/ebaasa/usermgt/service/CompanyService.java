package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.models.CompanyDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TCompany;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
public interface CompanyService {
    Optional<TCompany> addCompany(CompanyDto companyDto);
    Optional<TCompany> updateCompany(CompanyDto companyDto);
    Optional<TCompany> getCompanyById(Long id);
    Optional<TCompany> getCompanyByBusinessName(String businessName);
    List<TCompany> getAllCompanies(Pageable pageable);
}
