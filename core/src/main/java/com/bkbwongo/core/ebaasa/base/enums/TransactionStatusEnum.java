package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */

public enum TransactionStatusEnum {
    PENDING("Transaction has not been fully processed"),
    SUCCESSFUL("Transaction has been processed by all parties involved"),
    FAILED("Transaction processing has failed");

    private final String description;

    TransactionStatusEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return  this.description;
    }

}
