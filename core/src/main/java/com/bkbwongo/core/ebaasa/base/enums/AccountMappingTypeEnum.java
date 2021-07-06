package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 06/07/2021
 * @project ebaasa-sms
 */
public enum AccountMappingTypeEnum {
    SYSTEM("System account mapping, system can have multiple accounts of the same type"),
    USER("User account mapping, a user can only have one account of a specific type");

    String description;

    AccountMappingTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
