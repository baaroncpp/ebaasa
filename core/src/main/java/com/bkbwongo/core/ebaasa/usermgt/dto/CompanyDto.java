package com.bkbwongo.core.ebaasa.usermgt.dto;

import com.bkbwongo.core.ebaasa.dto.CountryDto;
import com.bkbwongo.core.ebaasa.dto.DistrictDto;
import com.bkbwongo.core.ebaasa.enums.IdentificationEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CompanyDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String businessName;
    private String natureOfBusiness;
    private String physicalAddress;
    private String phoneNumber;
    private DistrictDto district;
    private String tinNumber;
    private CountryDto registrationCountry;
    private String contactPerson;
    private IdentificationEnum contactIdentification;
    private String contactIdentificationNumber;
    private String contactIdentificationPath;
    private String contactPhoneNumber;
    private String email;
    private String formSerial;
    private String note;
}
