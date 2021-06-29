package com.bkbwongo.core.ebaasa.accountsmgt.api;

import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountLinkDto;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountMappingDto;
import com.bkbwongo.core.ebaasa.accountsmgt.service.AccountMappingService;
import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Accounts Mapping", description = "Manage mapping wallet, bank, sms accounts to user")
@RestController
@RequestMapping("/api")
public class AccountMappingApi {

    @Autowired
    private AccountMappingService accountMappingService;

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/accountmapping", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addAccountMapping(@RequestBody AccountMappingDto accountMappingDto){
        return ResponseEntity.ok(accountMappingService.addAccountMapping(accountMappingDto));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/accountmapping/link", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> linkAccount(@RequestBody AccountLinkDto accountLinkDto){
        return ResponseEntity.ok(accountMappingService.linkAccount(accountLinkDto));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/accountmapping/unlink", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> unlinkAccount(@RequestBody AccountLinkDto accountLinkDto){
        return ResponseEntity.ok(accountMappingService.unlinkAccount(accountLinkDto));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @GetMapping(value = "/accountmapping/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAccountMappingById(@PathVariable("id") Long id){
        return ResponseEntity.ok(accountMappingService.getAccountMappingById(id));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @GetMapping(value = "/accountmapping/{userid}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAccountMappingByUserId(@PathVariable("userid") Long id){
        return ResponseEntity.ok(accountMappingService.getAccountMappingByUserId(id));
    }
}
