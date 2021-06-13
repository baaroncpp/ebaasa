package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
public enum CashFlowEnum {
    MAIN_TO_BUSINESS("Money is leaving main wallet and going to user wallet"),
    STOCK_WITHDRAW("Money is leaving the system entirely"),
    BUSINESS_TO_MAIN("Money is leaving the user wallet and going to main wallet");

    private final String description;
    private CashFlowEnum(String description){
        this.description = description;
    }
}
