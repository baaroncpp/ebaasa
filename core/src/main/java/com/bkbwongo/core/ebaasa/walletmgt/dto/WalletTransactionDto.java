package com.bkbwongo.core.ebaasa.walletmgt.dto;

import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionDto {
    private String id;
    private WalletDto wallet;
    private TransactionTypeEnum transactionType;
    private Boolean nonReversal;
    private TransactionStatusEnum transactionStatus;
    private String statusDescription;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String externalTransactionId;
    private Date createdOn;
    private Date modifiedOn;
}
