package com.bkbwongo.core.ebaasa.jpa.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@MappedSuperclass
public class BaseEntity {
    private String id;
    private Date createdOn;
    private Date modifiedOn;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "created_on",insertable =false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "modified_on")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
