package com.bkbwongo.core.ebaasa.smsmgt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SmsAccountDto {
}
