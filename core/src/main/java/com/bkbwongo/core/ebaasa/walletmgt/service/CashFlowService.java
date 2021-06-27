package com.bkbwongo.core.ebaasa.walletmgt.service;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.dto.CashFlowDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
public interface CashFlowService {
    Optional<TCashFlow> initiateCashFlow(CashFlowDto cashFlowDto);
    Optional<TCashFlow> automaticCashFlow(CashFlowDto cashFlowDto);
    Optional<TCashFlow> cashFlowApproval1(Long id);
    Optional<TCashFlow> cashFlowApproval2(Long id);
    Optional<TCashFlow> rejectCashFlow(Long id);
    Optional<TCashFlow> getCashFlowById(Long id);
    List<TCashFlow> getDepositCashFlowsByWalletId(Long walletId, Pageable pageable);
    List<TCashFlow> getWithDrawCashFlowsByWalletId(Long walletId, Pageable pageable);
    List<TCashFlow> getCashFlows(Pageable pageable);
}
