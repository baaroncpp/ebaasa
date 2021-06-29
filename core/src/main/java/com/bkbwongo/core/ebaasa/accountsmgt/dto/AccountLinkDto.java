package com.bkbwongo.core.ebaasa.accountsmgt.dto;

import com.bkbwongo.common.utils.Validate;
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
public class AccountLinkDto {
    private Long accountMappingId;
    private Long entityId;
    private LinkType linkType;

    public void validate(){
        Validate.notNull(accountMappingId, "accountMappingId is required");
        Validate.notNull(entityId, "entityId is required");
        Validate.notNull(linkType, "linkType is required");
    }

    public enum LinkType{
        WALLET,
        SMS,
        BANK
    }
}
