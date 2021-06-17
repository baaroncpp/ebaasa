package com.bkbwongo.core.ebaasa.walletmgt.dto;

import com.bkbwongo.common.utils.Money;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.enums.CashFlowEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

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

}
