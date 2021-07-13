package com.bkbwongo.core.ebaasa.smsmgt.api;

import com.bkbwongo.core.ebaasa.smsmgt.service.SmsPackageTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bkaaron
 * @created on 13/07/2021
 * @project ebaasa-sms
 */
@Tag(name = "Cash flow", description = "Manage moving cash between accounts")
@RestController
@RequestMapping("/api")
public class SmsPackageTypeApi {

    @Autowired
    private SmsPackageTypeService smsPackageTypeService;
}
