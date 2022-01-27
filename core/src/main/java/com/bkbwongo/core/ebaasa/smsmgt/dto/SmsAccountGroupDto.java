package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.AccountTypeEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
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
public class SmsAccountGroupDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String name;
    private String note;
    private AccountTypeEnum accountType;
    private Boolean isDebited;
    private Long smsDebitLimit;

    public void validate(){
        Validate.notEmpty(name, "Account group name is not defined");
        Validate.notNull(note, "Account group note is not defined");
        Validate.notNull(accountType, "Account type is not defined");
        Validate.isTrue(smsDebitLimit < 1, "smsDebitLimit must be greater than zero");
    }
}
