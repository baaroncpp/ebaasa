package com.bkbwongo.core.ebaasa.bankmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 22/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_deposit_approval",schema = "core")
public class TBankDepositApproval extends AuditedEntity {
    private TBankDeposit bankDeposit;
    private TUser approver1;
    private TUser approver2;
    private Date firstApproveOn;
    private Boolean isFirstApproved;
    private Boolean isSecondApproved;
    private Date secondApproveOn;
    private ApprovalEnum status;
    private String note;
    private String note2;
    private Integer approvalCount;

    @Column(name = "is_first_approved")
    public Boolean getFirstApproved() {
        return isFirstApproved;
    }

    public void setFirstApproved(Boolean firstApproved) {
        isFirstApproved = firstApproved;
    }

    @Column(name = "is_second_approved")
    public Boolean getSecondApproved() {
        return isSecondApproved;
    }

    public void setSecondApproved(Boolean secondApproved) {
        isSecondApproved = secondApproved;
    }

    @JoinColumn(name = "deposit_id",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TBankDeposit getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(TBankDeposit bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    @JoinColumn(name = "approver_1",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getApprover1() {
        return approver1;
    }


    public void setApprover1(TUser approver1) {
        this.approver1 = approver1;
    }

    @JoinColumn(name = "approver_2",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getApprover2() {
        return approver2;
    }

    public void setApprover2(TUser approver2) {
        this.approver2 = approver2;
    }

    @Column(name = "first_approve_on")
    @Temporal(TemporalType.DATE)
    public Date getFirstApproveOn() {
        return firstApproveOn;
    }

    public void setFirstApproveOn(Date firstApproveOn) {
        this.firstApproveOn = firstApproveOn;
    }

    @Column(name = "second_approve_on")
    @Temporal(TemporalType.DATE)
    public Date getSecondApproveOn() {
        return secondApproveOn;
    }

    public void setSecondApproveOn(Date secondApproveOn) {
        this.secondApproveOn = secondApproveOn;
    }

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public ApprovalEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalEnum status) {
        this.status = status;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "approval_count")
    public Integer getApprovalCount() {
        return approvalCount;
    }

    public void setApprovalCount(Integer approvalCount) {
        this.approvalCount = approvalCount;
    }

    @Column(name = "note_2")
    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

}
