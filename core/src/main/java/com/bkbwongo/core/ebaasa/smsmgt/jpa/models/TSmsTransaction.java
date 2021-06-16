package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms_transaction",schema = "core")
public class TSmsTransaction extends BaseEntity {
}
