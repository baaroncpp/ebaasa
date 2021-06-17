package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Builder
@Entity
@Table(name = "t_wallet_transaction", schema = "core")
public class TWalletTransaction implements Serializable {

    private String id;
    private TWallet wallet;
    private TransactionTypeEnum transactionType;
    private Boolean nonReversal;
    private TransactionStatusEnum transactionStatus;
    private String statusDescription;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String externalTransactionId;
    private Date createdOn;
    private Date modifiedOn;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JoinColumn(name = "wallet_id",referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TWallet getWallet() {
        return wallet;
    }

    public void setWallet(TWallet wallet) {
        this.wallet = wallet;
    }

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "non_reversal")
    public Boolean getNonReversal() {
        return nonReversal;
    }

    public void setNonReversal(Boolean nonReversal) {
        this.nonReversal = nonReversal;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Column(name = "balance_before")
    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    @Column(name = "balance_after")
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Column(name = "external_transaction_id")
    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    @Column(name = "created_on",insertable = false,updatable = false)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "modified_on")
    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
