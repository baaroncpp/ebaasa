package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 16/07/2021
 * @project ebaasa-sms
 */
public enum SmsPackageFlowEnum {
    MAIN_TO_USER("Sms package is leaving main sms Account and going to user sms account"),
    USER_TO_MAIN("Sms package is leaving user sms Account and going to main sms account"),
    USER_TO_USER("Sms package is leaving the user sms Account and going to main Sms Account");

    private final String description;

    private SmsPackageFlowEnum(String description){
        this.description = description;
    }
}
