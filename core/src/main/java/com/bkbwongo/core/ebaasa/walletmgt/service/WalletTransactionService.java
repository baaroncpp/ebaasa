package com.bkbwongo.core.ebaasa.walletmgt.service;

import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
public interface WalletTransactionService {
    Optional<TWalletTransaction> getWalletTransactionById(String id);
    Optional<TWalletTransaction> getWalletTransactionByExternalTransactionId(String id);
    List<TWalletTransaction> getWalletTransactions(Long walletId, Pageable pageable);
}
