package com.bkbwongo.core.ebaasa.base.enums;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */
public enum CurrencyEnum {

    UGX("UG","Uganda Shillings"),
    KSH("KE","Kenya Shillings");

    private final String countryCode;
    private final String description;

    CurrencyEnum(String countryCode,String description){
        this.countryCode = countryCode;
        this.description = description;
    }

    public CurrencyEnum getCurrencyByCountryCode(String countryCode){
        for(CurrencyEnum v : values()){
            if(v.countryCode.equalsIgnoreCase(countryCode)){
                return v;
            }
        }
        return null;
    }

}
