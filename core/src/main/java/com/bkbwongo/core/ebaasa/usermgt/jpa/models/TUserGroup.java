package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import com.bkbwongo.core.ebaasa.jpa.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @created on 13/05/2021
 * @project ebaasa-sms
 * @author  bkaaron
 */
@Entity
@Table(name = "t_user_group", schema = "core")
public class TUserGroup extends BaseEntity {
    private String name;
    private String note;

    @Column(name = "user_group_name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "group_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
