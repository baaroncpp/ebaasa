package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_package_transaction",schema = "core")
public class TSmsPackageTransaction extends AuditedEntity {
    private TSmsAccount smsAccount;
    private TSmsPackage smsPackage;
    private TransactionTypeEnum transactionType;
    private TransactionStatusEnum status;
    private String statusDescription;
    private Boolean nonReversal;
    private Long numberSmsBefore;// full count os sum numbers in packages b4 transaction
    private Long numberSmsAfter;// full count os sum numbers in packages after transaction

    @JoinColumn(name = "sms_account_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsAccount getSmsAccount() {
        return smsAccount;
    }

    public void setSmsAccount(TSmsAccount smsAccount) {
        this.smsAccount = smsAccount;
    }

    @JoinColumn(name = "sms_package_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsPackage getSmsPackage() {
        return smsPackage;
    }

    public void setSmsPackage(TSmsPackage smsPackage) {
        this.smsPackage = smsPackage;
    }

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Column(name = "non_reversal")
    public Boolean getNonReversal() {
        return nonReversal;
    }

    public void setNonReversal(Boolean nonReversal) {
        this.nonReversal = nonReversal;
    }

    @Column(name = "number_sms_before")
    public Long getNumberSmsBefore() {
        return numberSmsBefore;
    }

    public void setNumberSmsBefore(Long numberSmsBefore) {
        this.numberSmsBefore = numberSmsBefore;
    }

    @Column(name = "number_sms_after")
    public Long getNumberSmsAfter() {
        return numberSmsAfter;
    }

    public void setNumberSmsAfter(Long numberSmsAfter) {
        this.numberSmsAfter = numberSmsAfter;
    }
}
