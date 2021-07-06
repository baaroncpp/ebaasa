package com.bkbwongo.core.ebaasa.accountsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.base.enums.AccountMappingTypeEnum;
import com.bkbwongo.core.ebaasa.base.enums.StatusEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_account_mapping", schema = "core")
public class TAccountMapping extends AuditedEntity {
    private TBankAccount bankAccount;
    private TUser user;
    private TSmsAccount smsAccount;
    private TWallet wallet;
    private StatusEnum status;
    private AccountMappingTypeEnum accountMappingTypeEnum;

    @JoinColumn(name = "bank_account_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(TBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }

    @JoinColumn(name = "sms_account_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TSmsAccount getSmsAccount() {
        return smsAccount;
    }

    public void setSmsAccount(TSmsAccount smsAccount) {
        this.smsAccount = smsAccount;
    }

    @JoinColumn(name = "wallet_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TWallet getWallet() {
        return wallet;
    }

    @Column(name = "")
    public void setWallet(TWallet wallet) {
        this.wallet = wallet;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(name = "account_mapping_type")
    @Enumerated(EnumType.STRING)
    public AccountMappingTypeEnum getAccountMappingEnum() {
        return accountMappingTypeEnum;
    }

    public void setAccountMappingEnum(AccountMappingTypeEnum accountMappingTypeEnum) {
        this.accountMappingTypeEnum = accountMappingTypeEnum;
    }
}
