package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletTransactionRepository;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 15/06/2021
 * @project ebaasa-sms
 */
@Service
public class WalletTransactionServiceImp implements WalletTransactionService {

    private TWalletRepository walletRepository;
    private TWalletTransactionRepository walletTransactionRepository;

    @Autowired
    public WalletTransactionServiceImp(TWalletRepository walletRepository,
                                       TWalletTransactionRepository walletTransactionRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    @Override
    public Optional<TWalletTransaction> getWalletTransactionById(Long id) {
        Validate.notNull(id, "Null ID value");
        var walletTransaction = walletTransactionRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format("Transaction with ID: %s not found", id))
                );
        return Optional.of(walletTransaction);
    }

    @Override
    public List<TWalletTransaction> getWalletTransactions(Long walletId, Pageable pageable) {
        Validate.notNull(walletId, "Null wallet ID value");
        var wallet = walletRepository.findById(walletId)
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s not found", walletId))
                );

        return walletTransactionRepository.findByWallet(wallet, pageable).getContent();
    }
}
