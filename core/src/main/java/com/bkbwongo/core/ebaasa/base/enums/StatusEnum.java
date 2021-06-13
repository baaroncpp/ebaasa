package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum StatusEnum {
    ACTIVE (Boolean.TRUE),
    NOT_ACTIVE (Boolean.FALSE);

    private final Boolean boolValue;
    StatusEnum(Boolean boolValue){
        this.boolValue = boolValue;
    }

    public Boolean getBoolValue(){
        return boolValue;
    }

    public static StatusEnum getFromBoolean(boolean bool){
        if(bool)
            return StatusEnum.ACTIVE;

        return StatusEnum.NOT_ACTIVE;
    }
}
