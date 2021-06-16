package com.bkbwongo.core.ebaasa.walletmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.enums.WalletAccountTypeEnum;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;

import javax.persistence.*;

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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "wallet_account_type")
    @Enumerated(EnumType.STRING)
    public WalletAccountTypeEnum getGroupType() {
        return groupType;
    }

    public void setGroupType(WalletAccountTypeEnum groupType) {
        this.groupType = groupType;
    }

    @Column(name = "is_debited")
    public boolean isDebited() {
        return isDebited;
    }

    public void setDebited(boolean debited) {
        isDebited = debited;
    }
}
