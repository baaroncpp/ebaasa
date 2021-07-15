package com.bkbwongo.core.ebaasa.smsmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsPackageTypeDto;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsPackageTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

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

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/smspackagetype", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addSmsPackageType(@RequestBody SmsPackageTypeDto smsPackageTypeDto){
        return ResponseEntity.ok(smsPackageTypeService.createSmsPackageType(smsPackageTypeDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/smspackagetype", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateSmsPackageType(@RequestBody SmsPackageTypeDto smsPackageTypeDto){
        return ResponseEntity.ok(smsPackageTypeService.updateSmsPackageType(smsPackageTypeDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/smspackagetype/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getSmsPackageType(@PathVariable Long id){
        return ResponseEntity.ok(smsPackageTypeService.getSmsPackageTypeBy(id));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PutMapping(value = "/smspackagetype/activate/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> activateSmsPackageType(@PathVariable Long id){
        return ResponseEntity.ok(smsPackageTypeService.activateSmsPackageType(id));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PutMapping(value = "/smspackagetype/close/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> closeSmsPackageType(@PathVariable Long id){
        return ResponseEntity.ok(smsPackageTypeService.closeSmsPackageType(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @PutMapping(value = "/smspackagetypes", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllSmsPackageType(){
        return ResponseEntity.ok(smsPackageTypeService.getSmsPackageTypes());
    }
}
