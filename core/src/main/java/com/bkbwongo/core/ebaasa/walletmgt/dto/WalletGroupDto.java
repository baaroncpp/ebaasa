package com.bkbwongo.core.ebaasa.walletmgt.dto;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.WalletAccountTypeEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletGroupDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private UserDto createdBy;
    private UserDto modifiedBy;
    private String name;
    private String note;
    private WalletAccountTypeEnum groupType;
    private boolean isDebited;

    public void validate(){
        Validate.notEmpty(name, "Null walletGroup name value");
        Validate.notNull(groupType, "Null group type value");
        Validate.notNull(isDebited, "Null isDebited value");

    }
}
