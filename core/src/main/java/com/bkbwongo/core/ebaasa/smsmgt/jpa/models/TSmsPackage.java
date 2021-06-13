package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_package",schema = "core")
public class TSmsPackage extends AuditedEntity {
    private TUser user;
    private TSmsPackageType smsPackageType;
    private Boolean isUsedUp;
    private Boolean isActive;
}
