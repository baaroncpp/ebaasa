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
@Table(name = "t_sms_package_type",schema = "core")
public class TSmsPackageType extends AuditedEntity {
    private String name;
    private String note;
    private int smsCount;
    private Boolean isActive;
}
