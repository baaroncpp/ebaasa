package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.WalletAccountTypeEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_wallet_group", schema = "core")
public class TWalletGroup extends AuditedEntity {
    private String name;//unique
    private String note;
    private WalletAccountTypeEnum groupType;
    private boolean isDebited;//find a word that can be invoiced on debt
}
