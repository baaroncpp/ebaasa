package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
public enum AccountStatusEnum {
    ACTIVE("Account is assigned to a user already"),
    NOT_ACTIVE("Account is not assigned to any party and can be used"),
    CLOSED("Account closed for ever and cannot be used"),
    SUSPENDED("Account is temporarily suspended but not closed");

    private final String description;

    AccountStatusEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
