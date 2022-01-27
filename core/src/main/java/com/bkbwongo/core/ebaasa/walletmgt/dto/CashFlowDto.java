package com.bkbwongo.core.ebaasa.walletmgt.dto;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Money;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.enums.CashFlowEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
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
public class CashFlowDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String externalReference;//MOMO transaction id, bank transaction id
    private Money amount;
    private WalletTransactionDto fromWalletTransaction;
    private WalletTransactionDto toWalletTransaction;
    private WalletDto fromWallet; // Decreasing in value
    private WalletDto toWallet; // Increasing in value
    private UserDto approveUser1;
    private UserDto approveUser2;
    private String note;
    private String note1;
    private String note2;
    private UserDto rejectedBy;
    private Date rejectedOn;
    private CashFlowEnum flowType;
    private Boolean isFirstApproved;
    private Boolean isSecondApproved;
    private Boolean isRejected;
    private Date firstApprovedOn;
    private Date secondApprovedOn;
    private Integer approvalCount;
    private ApprovalEnum status;

    public void validate(){
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, ErrorMessageConstants.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
        Validate.notNull(fromWallet, String.format(ErrorMessageConstants.NULL_OBJECT_VALUE, "debt wallet"));
        Validate.notNull(toWallet, String.format(ErrorMessageConstants.NULL_OBJECT_VALUE, "credit wallet"));
        Validate.notNull(flowType, "cash flow type not defined");
    }

}
