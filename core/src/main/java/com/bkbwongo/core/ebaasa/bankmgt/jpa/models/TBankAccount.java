package com.bkbwongo.core.ebaasa.bankmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;
import com.bkbwongo.core.ebaasa.base.jpa.models.TCountry;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 22/06/2021
 * @project ebaasa-sms
 */
@Table(name = "t_bank_account",schema = "core")
@Entity
public class TBankAccount extends BaseEntity {
    private String bankName;
    private String accountName;
    private String accountNumber;
    private String branch;
    private String swiftCode;
    private String currency;
    private TCountry country;
    private Boolean isAssigned;

    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "branch")
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Column(name = "swift_code")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JoinColumn(name = "country_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.EAGER)
    public TCountry getCountry() {
        return country;
    }

    public void setCountry(TCountry country) {
        this.country = country;
    }

    @Column(name = "is_assigned")
    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }
}
