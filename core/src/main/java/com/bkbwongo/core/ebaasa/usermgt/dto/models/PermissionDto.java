package com.bkbwongo.core.ebaasa.usermgt.dto.models;

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
public class PermissionDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private RoleDto role;
    private String name;

    public void validate(){
        Validate.notNull(role, "role is required");
        Validate.notEmpty(name, "permission name is required");
    }
}
