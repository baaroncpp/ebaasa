package com.bkbwongo.core.ebaasa.walletmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.security.JwtUtil;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 13/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Wallet Accounts", description = "Manage wallet accounts")
@RestController
@RequestMapping("/api")
public class WalletApi {

    @Autowired
    private WalletService walletService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/wallet", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addWallet(@RequestBody WalletDto walletDto){
        return ResponseEntity.ok(walletService.createWallet(walletDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/wallet", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateWallet(@RequestBody WalletDto walletDto){
        return ResponseEntity.ok(walletService.updateWallet(walletDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallet/{id}", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWallet(@PathVariable Long id){
        return ResponseEntity.ok(walletService.getWalletById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallets/group", params = { "page", "size" ,"sort", "walletgroupid"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllWalletsByGroup(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sort") String sort,
                                              @RequestParam("walletgroupid") Long walletGroupId){
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
        return ResponseEntity.ok(walletService.getWalletsByGroup(walletGroupId, pageable));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallets", params = { "page", "size" ,"sort"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllWallets(@RequestParam("page") int page,
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
        return ResponseEntity.ok(walletService.getAllWallets(pageable));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/wallet/activate", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> activateWallet(@RequestBody WalletDto walletDto){
        return ResponseEntity.ok(walletService.activateWallet(walletDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/wallet/suspend", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> suspendWallet(@RequestBody WalletDto walletDto){
        return ResponseEntity.ok(walletService.suspendWallet(walletDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/wallet/close", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> closeWallet(@RequestBody WalletDto walletDto){
        return ResponseEntity.ok(walletService.closeWallet(walletDto));
    }
}
