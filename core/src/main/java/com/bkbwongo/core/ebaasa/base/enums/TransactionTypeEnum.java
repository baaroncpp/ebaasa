package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum TransactionTypeEnum {
    SMS_PURCHASE("buying sms from using ebaasa wallet credit"),
    MOMO_DEPOSIT("depositing money from MOMO to ebaasa wallet"),
    REFUND("refunding money from ebaasa wallet to MOMO");

    private final String description;

    TransactionTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
