package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_wallet_account",schema = "core")
public class TWallet extends AuditedEntity {
    private String name;
    private String code;
    private TWalletGroup walletGroup;
    private BigDecimal balanceToNotifyAt;
    private Date balanceNotificationSentOn;
    private BigDecimal availableBalance;
    private AccountStatusEnum accountStatus;
    private String statusDescription;
    private Boolean isActive;
    private Boolean isSuspended;
    private Boolean isClosed;
    private Date activateOn;
    private TUser activatedBy;
    private Date suspendedOn;
    private TUser suspendedBy;
    private Date closedOn;
    private TUser closedBy;
    private Boolean isAssigned;

    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "is_suspended")
    public Boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    @Column(name = "is_closed")
    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JoinColumn(name = "grouping",referencedColumnName = "id",insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TWalletGroup getWalletGroup() {
        return walletGroup;
    }

    public void setWalletGroup(TWalletGroup walletGroup) {
        this.walletGroup = walletGroup;
    }

    @Column(name = "balance_to_notify_at")
    public BigDecimal getBalanceToNotifyAt() {
        return balanceToNotifyAt;
    }

    public void setBalanceToNotifyAt(BigDecimal balanceToNotifyAt) {
        this.balanceToNotifyAt = balanceToNotifyAt;
    }

    @Column(name = "balance_notification_sent_on")
    public Date getBalanceNotificationSentOn() {
        return balanceNotificationSentOn;
    }

    public void setBalanceNotificationSentOn(Date balanceNotificationSentOn) {
        this.balanceNotificationSentOn = balanceNotificationSentOn;
    }

    @Column(name = "available_balance")
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Column(name = "status")
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

    @Column(name = "activated_on")
    public Date getActivateOn() {
        return activateOn;
    }

    public void setActivateOn(Date activateOn) {
        this.activateOn = activateOn;
    }

    @JoinColumn(name = "activated_by",referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getActivatedBy() {
        return activatedBy;
    }


    public void setActivatedBy(TUser activatedBy) {
        this.activatedBy = activatedBy;
    }

    @Column(name = "suspended_on")
    public Date getSuspendedOn() {
        return suspendedOn;
    }

    public void setSuspendedOn(Date suspendedOn) {
        this.suspendedOn = suspendedOn;
    }

    @JoinColumn(name = "suspended_by",referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getSuspendedBy() {
        return suspendedBy;
    }

    public void setSuspendedBy(TUser suspendedBy) {
        this.suspendedBy = suspendedBy;
    }

    @Column(name = "closed_on")
    public Date getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(Date closedOn) {
        this.closedOn = closedOn;
    }

    @JoinColumn(name = "closed_by",referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(TUser closedBy) {
        this.closedBy = closedBy;
    }

    @Column(name = "is_assigned")
    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }
}
