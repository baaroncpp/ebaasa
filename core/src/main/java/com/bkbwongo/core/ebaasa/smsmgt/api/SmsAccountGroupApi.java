package com.bkbwongo.core.ebaasa.smsmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountGroupDto;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsAccountGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 15/07/2021
 * @project ebaasa-sms
 */
@Tag(name = "Sms Account Groups", description = "Manage CRUD operations sms account groups")
@RestController
@RequestMapping("/api/sms/account")
public class SmsAccountGroupApi {

    @Autowired
    private SmsAccountGroupService smsAccountGroupService;

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addSmsAccountGroup(@RequestBody SmsAccountGroupDto smsAccountGroupDto){
        return ResponseEntity.ok(smsAccountGroupService.addSmsAccountGroup(smsAccountGroupDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateSmsAccountGroup(@RequestBody SmsAccountGroupDto smsAccountGroupDto){
        return ResponseEntity.ok(smsAccountGroupService.updateSmsAccountGroup(smsAccountGroupDto));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @DeleteMapping(value = "/group/delete/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> deleteSmsAccountGroup(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountGroupService.deleteSmsAccountGroup(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/group/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getSmsAccountGroupById(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountGroupService.getSmsAccountGroupById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/groups", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllSmsAccountGroup(){
        return ResponseEntity.ok(smsAccountGroupService.getAllSmsAccountGroup());
    }
}
