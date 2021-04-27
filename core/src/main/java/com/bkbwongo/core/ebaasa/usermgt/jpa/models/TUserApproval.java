package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import com.bkbwongo.core.ebaasa.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.jpa.models.AuditedEntity;

import javax.persistence.*;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_user_approval", schema = "core")
public class TUserApproval extends AuditedEntity {

    private String userId;
    private ApprovalEnum status;

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public ApprovalEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalEnum status) {
        this.status = status;
    }

}
