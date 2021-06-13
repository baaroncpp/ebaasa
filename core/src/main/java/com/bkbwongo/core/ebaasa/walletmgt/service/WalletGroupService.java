package com.bkbwongo.core.ebaasa.walletmgt.service;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletGroupDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
public interface WalletGroupService {
    Optional<TWalletGroup> createWalletGroup(WalletGroupDto walletGroupDto, TUser creator);
    Optional<TWalletGroup> updateWalletGroup(WalletGroupDto walletGroupDto);
    Optional<TWalletGroup> getWalletGroupById(Long id);
    Optional<TWalletGroup> getWalletGroupByName(String name);
    List<TWalletGroup> getAllWalletGroups();
}
