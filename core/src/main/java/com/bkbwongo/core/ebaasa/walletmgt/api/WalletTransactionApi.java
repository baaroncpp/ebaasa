package com.bkbwongo.core.ebaasa.walletmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletTransactionService;
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
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Wallet Transactions", description = "Resource for wallet transactions")
@RestController
@RequestMapping("/api")
public class WalletTransactionApi {

    @Autowired
    private WalletTransactionService walletTransactionService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallet/transaction/{id}", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWalletTransaction(@PathVariable String id){
        return ResponseEntity.ok(walletTransactionService.getWalletTransactionById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallets/transactions", params = { "page", "size" ,"sort", "walletid"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWalletTransaction(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("sort") String sort,
                                                       @RequestParam("walletid") Long walletId){
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
        return ResponseEntity.ok(walletTransactionService.getWalletTransactions(walletId, pageable));
    }

}
