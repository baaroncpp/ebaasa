package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 22/06/2021
 * @project ebaasa-sms
 */
public enum WalletTimeDebitLimitEnum {
    ANNUAL_LIMIT("wallet account can be debited up to a period of one year"),
    HALF_LIMIT("wallet account can be debited up to a period of half year"),
    QUARTER_LIMIT("wallet account can be debited up to a period of quarter year");

    private final String description;

    WalletTimeDebitLimitEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
