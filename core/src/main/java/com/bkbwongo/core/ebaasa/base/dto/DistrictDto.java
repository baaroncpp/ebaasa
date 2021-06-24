package com.bkbwongo.core.ebaasa.base.dto;

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
public class DistrictDto {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;
    private CountryDto country;
    private String name;
    private String region;
}
