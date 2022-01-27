package com.bkbwongo.core.ebaasa.bankmgt.dto;

import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.CompanyDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BankDepositDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private CompanyDto company;
    private BigDecimal amountDeposited;
    private TransactionStatusEnum status;
    private String bankReference;
    private BankAccountDto bank;
    private String payslipImagePath;
    private Date payslipTimestamp;
    private String externalDepositorName;

    public void validate(){
        Validate.isTrue(amountDeposited.compareTo(BigDecimal.ZERO) > 0,"Amount should be greater than zero");
        Validate.notNull(bankReference, "A bank transaction ID reference is required");
        Validate.notNull(bank,"Bank for deposit is required");
        Validate.notNull(payslipTimestamp,"Payslip timestamp cannot be null");
        Validate.notEmpty(externalDepositorName,"Depositor is required");
        Date date = DateTimeUtil.getCurrentUTCTime();
        Validate.isTrue(payslipTimestamp.before(date),"Payslip cannot be created before record entry");
    }
}
