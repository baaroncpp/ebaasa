package com.bkbwongo.core.ebaasa.smsmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountDto;
import com.bkbwongo.core.ebaasa.smsmgt.service.SmsAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 15/07/2021
 * @project ebaasa-sms
 */
@Tag(name = "Sms Accounts", description = "Manage CRUD operations on sms accounts")
@RestController
@RequestMapping("/api/sms")
public class SmsAccountApi {

    @Autowired
    private SmsAccountService smsAccountService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/account", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addSmsAccount(@RequestBody SmsAccountDto smsAccountDto){
        return ResponseEntity.ok(smsAccountService.addSmsAccount(smsAccountDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/account", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateSmsAccount(@RequestBody SmsAccountDto smsAccountDto){
        return ResponseEntity.ok(smsAccountService.updateSmsAccount(smsAccountDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/account/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getSmsAccountById(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountService.getSmsAccountBy(id));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/account/activate/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> activateSmsAccount(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountService.activateSmsAccount(id));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/account/suspend/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> suspendSmsAccount(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountService.suspendSmsAccount(id));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/account/close/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> closeSmsAccount(@PathVariable("id") Long id){
        return ResponseEntity.ok(smsAccountService.closeSmsAccount(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/accounts", params = { "page", "size" ,"sort"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllCompanies(@RequestParam("page") int page,
                                                  @RequestParam("size") int size,
                                                  @RequestParam("sort") String sort){
        Pageable pageable = null;

        switch(sort) {
            case SORT_DESC :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY).descending());
                break;
            case SORT_ASC :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY).ascending());
                break;
            default :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY));
        }
        return ResponseEntity.ok(smsAccountService.getSmsAccounts(pageable));

    }


}
