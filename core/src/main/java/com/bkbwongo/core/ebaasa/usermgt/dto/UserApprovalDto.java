package com.bkbwongo.core.ebaasa.usermgt.dto;

import com.bkbwongo.core.ebaasa.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
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
public class UserApprovalDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private TUser createdBy;
    private TUser modifiedBy;
    private String userId;
    private ApprovalEnum status;
}
