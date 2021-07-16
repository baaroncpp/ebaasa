package com.bkbwongo.core.ebaasa.walletmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.security.JwtUtil;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.CashFlowDto;
import com.bkbwongo.core.ebaasa.walletmgt.service.CashFlowService;
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
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Cash flow", description = "Manage moving cash between accounts")
@RestController
@RequestMapping("/api")
public class CashFlowApi {

    @Autowired
    private CashFlowService cashFlowService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/cashflow/request", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> makeCashFlowRequest(@RequestBody CashFlowDto cashFlowDto){
        return ResponseEntity.ok(cashFlowService.initiateCashFlow(cashFlowDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/cashflow/firstapproval/{cashFlowId}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> makeFirstApproval( @PathVariable("cashFlowId") Long cashFlowId){
        return ResponseEntity.ok(cashFlowService.cashFlowApproval1(cashFlowId));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/cashflow/secondapproval/{cashFlowId}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> makeSecondApproval(@PathVariable("cashFlowId") Long cashFlowId){
        return ResponseEntity.ok(cashFlowService.cashFlowApproval2(cashFlowId));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/cashflow/reject/{cashFlowId}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> rejectCashFlow(@PathVariable("cashFlowId") Long cashFlowId){
        return ResponseEntity.ok(cashFlowService.rejectCashFlow(cashFlowId));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/cashflow/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getCashFlowById(@PathVariable("cashFlowId") Long id){
        return ResponseEntity.ok(cashFlowService.getCashFlowById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/cashflow/deposit", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getDepositCashFlowByWalletId(@RequestParam("page") int page,
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
        return ResponseEntity.ok(cashFlowService.getDepositCashFlowsByWalletId(walletId, pageable));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/cashflow/withdraw", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWithDrawCashFlowByWalletId(@RequestParam("page") int page,
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
        return ResponseEntity.ok(cashFlowService.getWithDrawCashFlowsByWalletId(walletId, pageable));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/cashflows", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getCashFlows(@RequestParam("page") int page,
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
        return ResponseEntity.ok(cashFlowService.getCashFlows(pageable));
    }

}
