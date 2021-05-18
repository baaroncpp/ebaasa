package com.bkbwongo.core.ebaasa.usermgt.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 15/05/2021
 * @project ebaasa-sms
 */
@Data
public class RoleDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private String name;
    private String note;
}
