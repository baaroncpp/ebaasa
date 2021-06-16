package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.dto.CashFlowDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import com.bkbwongo.core.ebaasa.walletmgt.service.CashFlowService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
@Service
public class CashFlowServiceImp implements CashFlowService {
    @Override
    public Optional<TCashFlow> initiateCashFlow(CashFlowDto cashFlowDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TCashFlow> automaticCashFlow(CashFlowDto cashFlowDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TCashFlow> cashFlowApproval1(Long id, TUser user) {
        return Optional.empty();
    }

    @Override
    public Optional<TCashFlow> cashFlowApproval2(Long id, TUser user) {
        return Optional.empty();
    }

    @Override
    public Optional<TCashFlow> getCashFlowById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TCashFlow> getCashFlowsByWalletId(Long walletId, Pageable pageable) {
        return null;
    }

    @Override
    public List<TCashFlow> getCashFlows(Pageable pageable) {
        return null;
    }
}
