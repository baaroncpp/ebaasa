package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import com.bkbwongo.core.ebaasa.jpa.models.BaseEntity;
import com.bkbwongo.core.ebaasa.enums.UserTypeEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_user", schema = "core")
public class TUser extends BaseEntity implements Serializable {

    private String username;
    private String password;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialExpired;
    private boolean approved;
    private boolean initialPasswordReset;
    private TUserAuthority userAuthority;
    private transient TUserMeta userMeta;
    private Boolean isDeleted;
    private String approvedBy;
    private UserTypeEnum userType;

    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "account_locked")
    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    @Column(name = "account_expired")
    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    @Column(name = "cred_expired")
    public boolean isCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    @JoinColumn(name = "username",referencedColumnName = "username",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(TUserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    @JoinColumn(name = "id",referencedColumnName = "user_id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUserMeta getUserMeta() {
        return userMeta;
    }

    public void setUserMeta(TUserMeta userMeta) {
        this.userMeta = userMeta;
    }

    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Column(name = "approved_by")
    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Column(name = "initial_password_reset")
    public boolean isInitialPasswordReset() {
        return initialPasswordReset;
    }

    public void setInitialPasswordReset(boolean initialPasswordReset) {
        this.initialPasswordReset = initialPasswordReset;
    }

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }
}
