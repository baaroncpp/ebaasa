package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.base.enums.StatusEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_account_mapping", schema = "core")
public class TAccountMapping extends AuditedEntity {
    private TBankAccount bankAccountId;
    private TUser userId;
    private TSmsAccount smsAccountId;
    private TWallet walletId;
    private StatusEnum status;
    private Boolean systemAccount = Boolean.FALSE;

    @JoinColumn(name = "bank_account_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TBankAccount getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(TBankAccount bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TUser getUserId() {
        return userId;
    }

    public void setUserId(TUser userId) {
        this.userId = userId;
    }

    @JoinColumn(name = "sms_account_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TSmsAccount getSmsAccountId() {
        return smsAccountId;
    }

    public void setSmsAccountId(TSmsAccount smsAccountId) {
        this.smsAccountId = smsAccountId;
    }

    @JoinColumn(name = "wallet_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TWallet getWalletId() {
        return walletId;
    }

    @Column(name = "")
    public void setWalletId(TWallet walletId) {
        this.walletId = walletId;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(name = "is_system_account")
    public Boolean getSystemAccount() {
        return systemAccount;
    }

    public void setSystemAccount(Boolean systemAccount) {
        this.systemAccount = systemAccount;
    }
}
