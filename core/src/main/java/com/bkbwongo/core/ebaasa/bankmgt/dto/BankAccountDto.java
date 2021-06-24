package com.bkbwongo.core.ebaasa.bankmgt.dto;

import com.bkbwongo.core.ebaasa.base.dto.CountryDto;
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
    private Date createdOn;
    private Date modifiedOn;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private String branch;
    private String swiftCode;
    private String currency;
    private CountryDto country;
}
