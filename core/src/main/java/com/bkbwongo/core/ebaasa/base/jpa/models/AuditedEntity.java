package com.bkbwongo.core.ebaasa.base.jpa.models;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@MappedSuperclass
public class AuditedEntity extends BaseEntity{

    private TUser createdBy;
    private TUser modifiedBy;

    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(TUser createdBy) {
        this.createdBy = createdBy;
    }

    @JoinColumn(name = "modified_by", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(TUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
