package com.bkbwongo.core.ebaasa.walletmgt.service;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 12/06/2021
 * @project ebaasa-sms
 */
public interface WalletService {
    Optional<TWallet> createWallet(WalletDto walletDto, TUser user);
    Optional<TWallet> updateWallet(WalletDto walletDto);
    Optional<TWallet> getWalletById(Long id);
    List<TWallet> getWalletsByGroup(Long walletGroupId, Pageable pageable);
    List<TWallet> getAllWallets(Pageable pageable);
    Optional<TWallet> activateWallet(WalletDto walletDto, TUser user);
    Optional<TWallet> suspendWallet(WalletDto walletDto, TUser use);
    Optional<TWallet> closeWallet(WalletDto walletDto, TUser use);
}
