package com.bkbwongo.core.ebaasa.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum AccountTypeEnum {
    MAIN_CASH("MC"),
    MAIN_SMS("MS"),
    CASH("CA"),
    SMS("SMS");

    private final String acronym;

    AccountTypeEnum(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
