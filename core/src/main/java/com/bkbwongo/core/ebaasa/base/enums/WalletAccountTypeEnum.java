package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum WalletAccountTypeEnum {
    MAIN_CASH("MC"),
    COMPANY_CASH("CC"),
    CASH("CA");

    private final String acronym;

    WalletAccountTypeEnum(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
