package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import com.bkbwongo.core.ebaasa.jpa.models.BaseEntity;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 13/05/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_group_authority", schema = "core")
public class TGroupAuthority extends BaseEntity {
    private TUserGroup userGroup;
    private TPermission permission;

    @JoinColumn(name = "user_group_name", referencedColumnName = "user_group_name", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(TUserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @JoinColumn(name = "permission_name", referencedColumnName = "permission_name", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TPermission getPermission() {
        return permission;
    }

    public void setPermission(TPermission permission) {
        this.permission = permission;
    }

}
