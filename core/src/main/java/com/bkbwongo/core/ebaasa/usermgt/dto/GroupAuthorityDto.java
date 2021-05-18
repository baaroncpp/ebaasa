package com.bkbwongo.core.ebaasa.usermgt.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 15/05/2021
 * @project ebaasa-sms
 */
@Data
public class GroupAuthorityDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserGroupDto userGroupDto;
    private PermissionDto permissionDto;
}
