package com.bkbwongo.core.ebaasa.walletmgt.dto;

import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;
import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String name;
    private String code;
    private WalletGroupDto walletGroupDto;
    private BigDecimal balanceToNotifyAt;
    private Date balanceNotificationSentOn;
    private BigDecimal availableBalance;
    private AccountStatusEnum accountStatus;
    private String statusDescription;
    private Date activateOn;
    private UserDto activatedBy;
    private Date suspendedOn;
    private UserDto suspendedBy;
    private Date closedOn;
    private UserDto closedBy;
    private Boolean isAssigned;
}
