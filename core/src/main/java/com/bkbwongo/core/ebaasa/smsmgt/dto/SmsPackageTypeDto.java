package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.bkbwongo.common.utils.Validate;
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
public class SmsPackageTypeDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String name;
    private String note;
    private int smsCount;
    private Boolean isActive;
    private Boolean isClosed;
    private UserDto activatedBy;
    private UserDto closedBy;

    public void validate(){
        Validate.notEmpty(name, "name is required");
        Validate.notEmpty(note, "not is required");
        Validate.isTrue(smsCount > 0, "sms count must be greater than zero");
    }
}
