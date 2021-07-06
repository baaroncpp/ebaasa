package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum TransactionTypeEnum {
    SMS_PURCHASE("buying sms from using ebaasa wallet credit"),
    SMS_DEBIT("sms leaving sms account"),
    SMS_CREDIT("sms coming to sms account"),
    MOMO_DEPOSIT("depositing money from MOMO to wallet"),
    MOMO_WITHDRAW("withdrawing money from wallet to MOMO"),
    WALLET_DEBIT("money leaving wallet"),
    WALLET_CREDIT("money coming to wallet"),
    REFUND("refunding money from ebaasa wallet to MOMO");

    private final String description;

    TransactionTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
