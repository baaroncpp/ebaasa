package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 06/07/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SmsDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private String title;
    private String message;
    private String sourceNumber;
    private String destinationNumber;
    private Boolean isDelivered;
    private Date deliveredOn;
    private SmsPackageDto smsPackage;
}
