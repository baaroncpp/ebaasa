package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.enums.SmsPackageFlowEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 16/07/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_package_flow",schema = "core")
public class TSmsPackageFlow extends AuditedEntity {
    private TSmsPackage smsPackage;
    private TSmsPackageTransaction fromPackageTransaction;
    private TSmsPackageTransaction toPackageTransaction;
    private TSmsAccount fromSmsAccount;
    private TSmsAccount toSmsAccount;
    private TUser approveUser1;
    private TUser approveUser2;
    private String note;
    private String note1;
    private String note2;
    private TUser rejectedBy;
    private Date rejectedOn;
    private SmsPackageFlowEnum flowType;
    private Boolean isFirstApproved;
    private Boolean isSecondApproved;
    private Boolean isRejected;
    private Date firstApprovedOn;
    private Date secondApprovedOn;
    private Integer approvalCount;
    private ApprovalEnum status;

    @JoinColumn(name = "sms_package_id", referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsPackage getSmsPackage() {
        return smsPackage;
    }

    public void setSmsPackage(TSmsPackage smsPackage) {
        this.smsPackage = smsPackage;
    }

    @JoinColumn(name = "from_package_transaction_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsPackageTransaction getFromPackageTransaction() {
        return fromPackageTransaction;
    }

    public void setFromPackageTransaction(TSmsPackageTransaction fromPackageTransaction) {
        this.fromPackageTransaction = fromPackageTransaction;
    }

    @JoinColumn(name = "to_package_transaction_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsPackageTransaction getToPackageTransaction() {
        return toPackageTransaction;
    }

    public void setToPackageTransaction(TSmsPackageTransaction toPackageTransaction) {
        this.toPackageTransaction = toPackageTransaction;
    }

    @JoinColumn(name = "from_sms_account_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsAccount getFromSmsAccount() {
        return fromSmsAccount;
    }

    public void setFromSmsAccount(TSmsAccount fromSmsAccount) {
        this.fromSmsAccount = fromSmsAccount;
    }

    @JoinColumn(name = "to_sms_account_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsAccount getToSmsAccount() {
        return toSmsAccount;
    }

    public void setToSmsAccount(TSmsAccount toSmsAccount) {
        this.toSmsAccount = toSmsAccount;
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

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "note_1")
    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    @Column(name = "note_2")
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

    @Column(name = "rejected_on")
    public Date getRejectedOn() {
        return rejectedOn;
    }

    public void setRejectedOn(Date rejectedOn) {
        this.rejectedOn = rejectedOn;
    }

    @Column(name = "flow_type")
    @Enumerated(EnumType.STRING)
    public SmsPackageFlowEnum getFlowType() {
        return flowType;
    }

    public void setFlowType(SmsPackageFlowEnum flowType) {
        this.flowType = flowType;
    }

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

    @Column(name = "rejected_on")
    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
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
}
