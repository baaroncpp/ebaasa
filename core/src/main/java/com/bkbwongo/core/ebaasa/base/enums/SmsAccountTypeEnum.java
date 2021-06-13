package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
public enum SmsAccountTypeEnum {
    MAIN_SMS("MS"),
    COMPANY_SMS("CS"),
    SMS("SMS");

    private final String acronym;

    SmsAccountTypeEnum(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
