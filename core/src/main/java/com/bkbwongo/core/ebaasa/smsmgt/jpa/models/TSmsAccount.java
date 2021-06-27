package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_account",schema = "core")
public class TSmsAccount extends AuditedEntity {
}
