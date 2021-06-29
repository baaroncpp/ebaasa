package com.bkbwongo.core.ebaasa.accountsmgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountMappingDto {
    private Long id;
    private Long bankAccountId;
    private Long userId;
    private Long smsAccountId;
    private Long walletId;
    private StatusEnum status;
    private Boolean systemAccount = Boolean.FALSE;

    public void validate(){
        Validate.notNull(userId,"user ID is required");
        Validate.notNull(status,"status is required");
    }
}
