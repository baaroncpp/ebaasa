package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.common.utils.Money;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.enums.CashFlowEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_cash_flow",schema = "core")
public class TCashFlow extends AuditedEntity {

    private String externalReference;//MOMO transaction id, bank transaction id
    private Money amount;
    private TWalletTransaction fromWalletTransaction;
    private TWalletTransaction toWalletTransaction;
    private TWallet fromWallet; // Decreasing in value
    private TWallet toWallet; // Increasing in value
    private TUser approveUser1;
    private TUser approveUser2;
    private String note;
    private String note1;
    private String note2;
    private TUser rejectedBy;
    private CashFlowEnum flowType;
    private Boolean isFirstApproved;
    private Boolean isSecondApproved;
    private Date firstApprovedOn;
    private Date secondApprovedOn;
    private Integer approvalCount;
    private ApprovalEnum status;

    @Column(name = "first_approved")
    public Boolean getFirstApproved() {
        return isFirstApproved;
    }

    public void setFirstApproved(Boolean firstApproved) {
        isFirstApproved = firstApproved;
    }

    @Column(name = "second_approved")
    public Boolean getSecondApproved() {
        return isSecondApproved;
    }

    public void setSecondApproved(Boolean secondApproved) {
        isSecondApproved = secondApproved;
    }

    @Column(name = "external_reference")
    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    @Column(name = "amount")
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @JoinColumn(name = "from_wallet_transaction_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TWalletTransaction getFromWalletTransaction() {
        return fromWalletTransaction;
    }

    public void setFromWalletTransaction(TWalletTransaction fromWalletTransaction) {
        this.fromWalletTransaction = fromWalletTransaction;
    }

    @JoinColumn(name = "to_wallet_transaction_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TWalletTransaction getToWalletTransaction() {
        return toWalletTransaction;
    }

    public void setToWalletTransaction(TWalletTransaction toWalletTransaction) {
        this.toWalletTransaction = toWalletTransaction;
    }

    @JoinColumn(name = "from_wallet_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TWallet getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(TWallet fromWallet) {
        this.fromWallet = fromWallet;
    }

    @JoinColumn(name = "approve_user_1",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getApproveUser1() {
        return approveUser1;
    }

    public void setApproveUser1(TUser approveUser1) {
        this.approveUser1 = approveUser1;
    }

    @JoinColumn(name = "approve_user_2",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getApproveUser2() {
        return approveUser2;
    }

    public void setApproveUser2(TUser approveUser2) {
        this.approveUser2 = approveUser2;
    }

    @Column(name = "note1")
    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    @Column(name = "")
    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    @JoinColumn(name = "rejected_by",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(TUser rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    @Column(name = "flow_type")
    public CashFlowEnum getFlowType() {
        return flowType;
    }

    public void setFlowType(CashFlowEnum flowType) {
        this.flowType = flowType;
    }

    @Column(name = "first_approve_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFirstApprovedOn() {
        return firstApprovedOn;
    }

    public void setFirstApprovedOn(Date firstApprovedOn) {
        this.firstApprovedOn = firstApprovedOn;
    }

    @Column(name = "second_approve_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSecondApprovedOn() {
        return secondApprovedOn;
    }

    public void setSecondApprovedOn(Date secondApprovedOn) {
        this.secondApprovedOn = secondApprovedOn;
    }

    @Column(name = "approval_count")
    public Integer getApprovalCount() {
        return approvalCount;
    }

    public void setApprovalCount(Integer approvalCount) {
        this.approvalCount = approvalCount;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public ApprovalEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalEnum status) {
        this.status = status;
    }

    @JoinColumn(name = "to_wallet_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TWallet getToWallet() {
        return toWallet;
    }

    public void setToWallet(TWallet toWallet) {
        this.toWallet = toWallet;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
