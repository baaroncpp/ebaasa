package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SmsAccountDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String name;
    private SmsAccountGroupDto smsAccountType;
    private Date isCosedOn;
    private UserDto closedBy;
    private UserDto activatedBy;
    private Date activatedOn;
    private Boolean isClosed;
    private Boolean isActive;
    private Long accountSmsCount;
    private Boolean isAssigned;
    private UserDto assignedBy;

    public void validate(){
        Validate.notNull(smsAccountType, "Please define sms account type");
        Validate.notEmpty(name, "Sms account name not defined");
    }
}
