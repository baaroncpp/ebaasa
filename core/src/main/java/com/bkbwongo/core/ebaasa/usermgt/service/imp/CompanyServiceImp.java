package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.CompanyDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TCompany;
import com.bkbwongo.core.ebaasa.usermgt.repository.TCompanyRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Service
public class CompanyServiceImp implements CompanyService {

    private TCompanyRepository tCompanyRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;
    private AuditService auditService;

    @Autowired
    public CompanyServiceImp(TCompanyRepository tCompanyRepository,
                             UserManagementDTOMapperService userManagementDTOMapperService,
                             AuditService auditService) {
        this.tCompanyRepository = tCompanyRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
        this.auditService = auditService;
    }

    @Override
    public Optional<TCompany> addCompany(CompanyDto companyDto) {

        companyDto.validate();

        var company = tCompanyRepository.findByBusinessName(companyDto.getBusinessName());
        if (company.isPresent()){
            throw new BadRequestException(String.format(ErrorMessageConstants.BUSINESS_NAME_TAKEN, companyDto.getBusinessName()));
        }

        var result = userManagementDTOMapperService.convertDTOToTCompany(companyDto);
        auditService.stampAuditedEntity(result);

        return Optional.of(
                tCompanyRepository.save(result)
        );
    }

    @Override
    public Optional<TCompany> updateCompany(CompanyDto companyDto) {

        companyDto.validate();

        var company = tCompanyRepository.findById(companyDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Company with ID %s does not exist", companyDto.getId()))
                );

        if (!company.getBusinessName().equalsIgnoreCase(companyDto.getBusinessName())){
            var companyCheck = tCompanyRepository.findByBusinessName(companyDto.getBusinessName());
            if (companyCheck.isPresent()){
                throw new BadRequestException(String.format("Company with business name %s already exists", companyDto.getBusinessName()));
            }
        }

        var companyResult = userManagementDTOMapperService.convertDTOToTCompany(companyDto);
        auditService.stampAuditedEntity(companyResult);

        return Optional.of(tCompanyRepository.save(companyResult));
    }

    @Override
    public Optional<TCompany> getCompanyById(Long id) {
        Validate.notNull(id, "Company ID not defined");

        var company = tCompanyRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format("Company with ID %s not found", id))
                );
        return Optional.of(company);
    }

    @Override
    public Optional<TCompany> getCompanyByBusinessName(String businessName) {

        var company = tCompanyRepository.findByBusinessName(businessName)
            .orElseThrow(
                    () -> new BadRequestException(String.format(ErrorMessageConstants.BUSINESS_NAME_NOT_FOUND, businessName))
            );
        return Optional.of(company);
    }

    @Override
    public List<TCompany> getAllCompanies(Pageable pageable) {
        return tCompanyRepository.findAll(pageable).getContent();
    }
}
