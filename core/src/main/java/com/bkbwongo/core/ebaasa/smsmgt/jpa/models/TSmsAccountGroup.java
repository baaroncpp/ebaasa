package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.AccountTypeEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 06/07/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_account_group", schema = "core")
public class TSmsAccountGroup extends AuditedEntity {
    private String name;
    private String note;
    private AccountTypeEnum accountType;
    private Boolean isDebited;
    private Long smsDebitLimit;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    @Column(name = "is_debited")
    public Boolean getDebited() {
        return isDebited;
    }

    public void setDebited(Boolean debited) {
        isDebited = debited;
    }

    @Column(name = "sms_debit_limit")
    public Long getSmsDebitLimit() {
        return smsDebitLimit;
    }

    public void setSmsDebitLimit(Long smsDebitLimit) {
        this.smsDebitLimit = smsDebitLimit;
    }
}
