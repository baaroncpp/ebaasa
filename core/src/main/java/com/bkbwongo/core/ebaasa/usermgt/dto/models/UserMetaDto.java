package com.bkbwongo.core.ebaasa.usermgt.dto.models;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.GenderEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.TCountry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 19/05/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserMetaDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String firstName;
    private String lastName;
    private String middleName;
    private Long userId;
    private String phoneNumber;
    private String phoneNumber2;
    private String imagePath;
    private String displayName;
    private GenderEnum gender;
    private Date birthDate;
    private String email;
    private TCountry countryCode;
    private String identification;
    private String identificationNumber;
    private String identificationPath;
    private Boolean nonVerifiedEmail;
    private Boolean nonVerifiedPhoneNumber;

    public void validate(){
        Validate.notEmpty(firstName, "first name is required");
        Validate.notEmpty(lastName,"last name is required");
        Validate.notNull(userId, "userId is required");
        Validate.notNull(countryCode, "country is required");
    }

}
