package com.bkbwongo.core.ebaasa.bankmgt.dto;

import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BankDepositApprovalDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private BankDepositDto bankDeposit;
    private UserDto approver1;
    private UserDto approver2;
    private Date firstApproveOn;
    private Boolean isFirstApproved;
    private Boolean isSecondApproved;
    private Date secondApproveOn;
    private ApprovalEnum status;
    private String note;
    private String note2;
    private Integer approvalCount;
}
