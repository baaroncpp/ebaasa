package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_account",schema = "core")
public class TSmsAccount extends AuditedEntity {
    private String name;
    private TSmsAccountGroup smsAccountType;
    private Date isCosedOn;
    private TUser closedBy;
    private TUser activatedBy;
    private Date activatedOn;
    private Boolean isClosed;
    private Boolean isActive;
    private Long accountSmsCount;
    private Boolean isAssigned;
    private TUser assignedBy;
    private AccountStatusEnum accountStatus;
    private String statusDescription;
    private TUser suspendedBy;
    private Boolean isSuspended;
    private Date suspendedOn;

    @Column(name = "sms_account_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinColumn(name = "sms_account_group_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TSmsAccountGroup getSmsAccountType() {
        return smsAccountType;
    }

    public void setSmsAccountType(TSmsAccountGroup smsAccountType) {
        this.smsAccountType = smsAccountType;
    }

    @Column(name = "closed_on")
    public Date getIsCosedOn() {
        return isCosedOn;
    }

    public void setIsCosedOn(Date isCosedOn) {
        this.isCosedOn = isCosedOn;
    }

    @JoinColumn(name = "closed_by", referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(TUser closedBy) {
        this.closedBy = closedBy;
    }

    @JoinColumn(name = "activated_by", referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getActivatedBy() {
        return activatedBy;
    }

    public void setActivatedBy(TUser activatedBy) {
        this.activatedBy = activatedBy;
    }

    @Column(name = "is_closed")
    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "account_sms_count")
    public Long getAccountSmsCount() {
        return accountSmsCount;
    }

    public void setAccountSmsCount(Long accountSmsCount) {
        this.accountSmsCount = accountSmsCount;
    }

    @Column(name = "is_assigned")
    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }

    @JoinColumn(name = "assigned_by", referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(TUser assignedBy) {
        this.assignedBy = assignedBy;
    }

    @Column(name = "activated_on")
    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @JoinColumn(name = "suspended_by", referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getSuspendedBy() {
        return suspendedBy;
    }

    public void setSuspendedBy(TUser suspendedBy) {
        this.suspendedBy = suspendedBy;
    }

    @Column(name = "is_suspended")
    public Boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    @Column(name = "suspended_on")
    public Date getSuspendedOn() {
        return suspendedOn;
    }

    public void setSuspendedOn(Date suspendedOn) {
        this.suspendedOn = suspendedOn;
    }
}
