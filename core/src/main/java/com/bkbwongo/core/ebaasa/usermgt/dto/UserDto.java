package com.bkbwongo.core.ebaasa.usermgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.UserTypeEnum;
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
public class UserDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private String username;
    private String password;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialExpired;
    private boolean approved;
    private boolean initialPasswordReset;
    private UserGroupDto userGroup;
    private Boolean isDeleted;
    private String approvedBy;
    private UserTypeEnum userType;

    public void validate(){
        Validate.notEmpty(username, "username is required");
        Validate.notEmpty(password, "password is required");
        Validate.notNull(userGroup, "user group is required");
    }
}
