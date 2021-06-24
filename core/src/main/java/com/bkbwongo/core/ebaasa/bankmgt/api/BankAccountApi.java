package com.bkbwongo.core.ebaasa.bankmgt.api;

import com.bkbwongo.core.ebaasa.bankmgt.dto.BankAccountDto;
import com.bkbwongo.core.ebaasa.bankmgt.service.BankAccountService;
import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Bank Accounts", description = "Manage all bank accounts, CRUD operations")
@RestController
@RequestMapping("/api")
public class BankAccountApi {

    @Autowired
    private BankAccountService bankAccountService;

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/bank", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addBankAccount(@RequestBody BankAccountDto bankAccountDto){
        return ResponseEntity.ok(bankAccountService.addBankAccount(bankAccountDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/bank", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateBankAccount(@RequestBody BankAccountDto bankAccountDto){
        return ResponseEntity.ok(bankAccountService.updateBankAccount(bankAccountDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/bank/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getBankAccountById(@PathVariable("id") Long id){
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/banks", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllBankAccounts(){
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

}
