package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 06/07/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SmsPackageDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private Long packageSmsCount;
    private SmsAccountDto smsAccount;
    private SmsPackageTypeDto smsPackageType;
    private Boolean isUsedUp;
    private Boolean isActive;
    private Date usedUpOn;
    private Date activatedOn;
}
