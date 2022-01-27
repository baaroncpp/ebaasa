package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
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
public class SmsPackageTransactionDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private SmsAccountDto smsAccount;
    private SmsPackageDto smsPackage;
    private TransactionTypeEnum transactionType;
    private TransactionStatusEnum status;
    private String statusDescription;
    private Boolean nonReversal;
    private Long numberSmsBefore;
    private Long numberSmsAfter;
}
