package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
public enum SmsPackageTypeEnum {
    LIMITED("package with a fixed sms amount"),
    UNLIMITED("package with a unlimited sms amount");

    private String description;

    SmsPackageTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
