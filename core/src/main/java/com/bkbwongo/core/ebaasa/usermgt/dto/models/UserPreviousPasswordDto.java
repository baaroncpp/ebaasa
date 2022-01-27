package com.bkbwongo.core.ebaasa.usermgt.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created 27/01/2022 - 9:54 AM
 * @project ebaasa
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserPreviousPasswordDto {
    private Long id;
    private UserDto user;
    private UserDto modifiedBy;
    private String password;
    private Date removalTime;
    private String note;
}
