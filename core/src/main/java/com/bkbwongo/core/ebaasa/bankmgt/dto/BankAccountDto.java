package com.bkbwongo.core.ebaasa.bankmgt.dto;

import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.dto.CountryDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BankAccountDto {
    private Long id;
    @JsonFormat( pattern = DateTimeUtil.DD_MM_YYYY)
    private Date createdOn;
    @JsonFormat( pattern = DateTimeUtil.DD_MM_YYYY)
    private Date modifiedOn;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private String branch;
    private String swiftCode;
    private String currency;
    private CountryDto country;

    public void validate(){
        Validate.notEmpty(bankName, "bank name is required");
        Validate.notEmpty(accountName, "account name is required");
        Validate.notEmpty(accountNumber, "account number is required");
        Validate.notEmpty(branch, "bank branch name is required");
    }
}
