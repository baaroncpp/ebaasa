package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_package",schema = "core")
public class TSmsPackage extends AuditedEntity {
    private Long packageSmsCount;
    private TSmsAccount smsAccount;
    private TSmsPackageType smsPackageType;
    private Boolean isUsedUp;
    private Boolean isActive;
    private Date usedUpOn;
    private Date activatedOn;

    @Column(name = "package_sms_count")
    public Long getPackageSmsCount() {
        return packageSmsCount;
    }

    public void setPackageSmsCount(Long packageSmsCount) {
        this.packageSmsCount = packageSmsCount;
    }

    @JoinColumn(name = "sms_account_id", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    public TSmsAccount getSmsAccount() {
        return smsAccount;
    }

    public void setSmsAccount(TSmsAccount smsAccount) {
        this.smsAccount = smsAccount;
    }

    @Column(name = "sms_package_type")
    public TSmsPackageType getSmsPackageType() {
        return smsPackageType;
    }

    public void setSmsPackageType(TSmsPackageType smsPackageType) {
        this.smsPackageType = smsPackageType;
    }

    @Column(name = "is_used_up")
    public Boolean getUsedUp() {
        return isUsedUp;
    }

    public void setUsedUp(Boolean usedUp) {
        isUsedUp = usedUp;
    }

    @Column(name = "is_active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "used_up_on")
    public Date getUsedUpOn() {
        return usedUpOn;
    }

    public void setUsedUpOn(Date usedUpOn) {
        this.usedUpOn = usedUpOn;
    }

    @Column(name = "activated_on")
    public Date getActiveOn() {
        return activatedOn;
    }

    public void setActiveOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }
}
