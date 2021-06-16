package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.usermgt.dto.CompanyDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TCompany;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.repository.TCompanyRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private TUserRepository tUserRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;

    private static final String ID_NOT_FOUND = "User with ID: %s not found";
    private static final String BUSINESS_NOT_FOUND = "Business name not defined";

    @Autowired
    public CompanyServiceImp(TCompanyRepository tCompanyRepository,
                             TUserRepository tUserRepository,
                             UserManagementDTOMapperService userManagementDTOMapperService) {
        this.tCompanyRepository = tCompanyRepository;
        this.tUserRepository = tUserRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
    }

    @Override
    public Optional<TCompany> addCompany(CompanyDto companyDto) {
        Validate.notNull(companyDto, "NULL company object");
        Validate.notEmpty(companyDto.getBusinessName(), BUSINESS_NOT_FOUND);
        Validate.notEmpty(companyDto.getContactPerson(), "Contact person not defined");// CONTACT PERSON IS USERNAME OF USER OWNING COMPANY
        Validate.notNull(companyDto.getCreatedBy(), "User creating the company not defined");

        var company = tCompanyRepository.findByBusinessName(companyDto.getBusinessName());
        if (company.isPresent()){
            throw new BadRequestException(String.format("Company with BUSINESS NAME %s already exists", companyDto.getBusinessName()));
        }

        tUserRepository.findById(companyDto.getCreatedBy().getId())
                .orElseThrow(
                        () -> new BadRequestException(ID_NOT_FOUND, companyDto.getCreatedBy().getId())
                );

        var result = userManagementDTOMapperService.convertDTOToTCompany(companyDto);
        result.setCreatedOn(new Date());

        return Optional.of(
                tCompanyRepository.save(result)
        );
    }

    @Override
    public Optional<TCompany> updateCompany(TUser modifiedBy, CompanyDto companyDto) {
        Validate.notNull(companyDto, "NULL company object");
        Validate.notEmpty(companyDto.getBusinessName(), BUSINESS_NOT_FOUND);
        Validate.notEmpty(companyDto.getContactPerson(), "Contact person not defined");
        Validate.notNull(companyDto.getCreatedBy(), "User creating the company not defined");
        Validate.notNull(companyDto.getId(), "Company ID not defined");

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

        tUserRepository.findById(modifiedBy.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ID_NOT_FOUND, modifiedBy.getId()))
                );

        var companyResult = userManagementDTOMapperService.convertDTOToTCompany(companyDto);
        companyResult.setModifiedOn(new Date());
        companyResult.setModifiedBy(modifiedBy);

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
        Validate.notEmpty(businessName, BUSINESS_NOT_FOUND);

        var company = tCompanyRepository.findByBusinessName(businessName)
            .orElseThrow(
                    () -> new BadRequestException(String.format("Company with BUSINESS NAME %s does not exist", businessName))
            );
        return Optional.of(company);
    }

    @Override
    public List<TCompany> getAllCompanies(Pageable pageable) {
        return tCompanyRepository.findAll(pageable).getContent();
    }
}
