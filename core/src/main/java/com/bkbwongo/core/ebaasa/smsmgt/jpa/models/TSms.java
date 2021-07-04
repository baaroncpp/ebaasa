package com.bkbwongo.core.ebaasa.smsmgt.jpa.models;

import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bkaaron
 * @created on 07/06/2021
 * @project ebaasa-sms
 */
@Entity
@Table(name = "t_sms", schema = "core")
public class TSms extends BaseEntity {
    private String title;
    private String message;
    private String sourceNumber;
    private String destinationNumber;
    private Boolean isDelivered;
    private Date deliveredOn;
    private TSmsPackage smsPackage;

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "source_number")
    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    @Column(name = "destination_number")
    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    @Column(name = "is_delivered")
    public Boolean getDelivered() {
        return isDelivered;
    }

    public void setDelivered(Boolean delivered) {
        isDelivered = delivered;
    }

    @Column(name = "delivered_on")
    public Date getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(Date deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    @JoinColumn(name = "sms_package_id", referencedColumnName = "id", updatable = true, insertable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    public TSmsPackage getSmsPackage() {
        return smsPackage;
    }

    public void setSmsPackage(TSmsPackage smsPackage) {
        this.smsPackage = smsPackage;
    }
}
