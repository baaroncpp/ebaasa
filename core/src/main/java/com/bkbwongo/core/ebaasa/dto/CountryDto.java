package com.bkbwongo.core.ebaasa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private String name;
    private String isoAlpha2;
    private String isoAlpha3;
    private Integer countryCode;
}
