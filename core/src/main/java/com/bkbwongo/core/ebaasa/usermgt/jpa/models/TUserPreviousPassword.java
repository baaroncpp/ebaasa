package com.bkbwongo.core.ebaasa.usermgt.jpa.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 24/04/2021
 * @project ebaasa-sms
 */

@Entity
@Table(name = "t_user_previous_password", schema = "core")
public class TUserPreviousPassword implements Serializable {

    private Long id;
    private TUser user;
    private TUser modifiedBy;
    private String password;
    private Date removalTime;
    private String note;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = true,updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "removal_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(Date removalTime) {
        this.removalTime = removalTime;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JoinColumn(name = "modifiedBy", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(TUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
