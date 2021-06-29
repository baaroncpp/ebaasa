package com.bkbwongo.core.ebaasa.usermgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 15/05/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GroupAuthorityDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserGroupDto userGroupDto;
    private PermissionDto permissionDto;

    public void validate(){
        Validate.notNull(userGroupDto, "user group is required");
        Validate.notNull(permissionDto, "user permission is not defined");
    }
}
