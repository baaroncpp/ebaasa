package com.bkbwongo.core.ebaasa.walletmgt.dto.service;

import com.bkbwongo.core.ebaasa.base.service.CoreDTOService;
import com.bkbwongo.core.ebaasa.usermgt.dto.RoleDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletGroupDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Component
public class WalletManagementDTOMapperService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreDTOService coreDTOService;

    public WalletGroupDto convertTWalletGroupToDTO(TWalletGroup tWalletGroup){
        return modelMapper.map(tWalletGroup, WalletGroupDto.class);
    }

    public TWalletGroup convertDTOToTWalletGroup(WalletGroupDto walletGroupDto){
        return modelMapper.map(walletGroupDto, TWalletGroup.class);
    }

    public WalletDto convertTWalletToDTO(TWallet tWallet){
        var walletDto = modelMapper.map(tWallet, WalletDto.class);
        walletDto.setWalletGroupDto(convertTWalletGroupToDTO(tWallet.getWalletGroup()));
        return walletDto;
    }

    public TWallet convertDTOToTWallet(WalletDto walletDto){
        var wallet = modelMapper.map(walletDto, TWallet.class);
        wallet.setWalletGroup(convertDTOToTWalletGroup(walletDto.getWalletGroupDto()));
        return wallet;
    }

}
