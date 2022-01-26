package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 13/05/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_permission", schema = "core")
public class TPermission extends BaseEntity {
    private TRole role;
    private String name;

    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)//MANY PERMISSIONS TO ONE ROLE
    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }

    @Column(name = "permission_name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
