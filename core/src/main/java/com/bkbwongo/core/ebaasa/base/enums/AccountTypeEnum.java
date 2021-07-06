package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum AccountTypeEnum {
    MAIN_WALLET("MW"),
    WALLET("WA"),
    MAIN_SMS("WS"),
    SMS("SMS");

    private final String acronym;

    AccountTypeEnum(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
