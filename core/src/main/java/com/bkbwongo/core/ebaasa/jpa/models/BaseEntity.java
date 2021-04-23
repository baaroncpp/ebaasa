package com.bkbwongo.core.ebaasa.jpa.models;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@MappedSuperclass
public class BaseEntity {
    private String id;
    private LocalDate createdOn;
    private LocalDate modifiedOn;
}
