package com.bkbwongo.core.ebaasa.walletmgt.dto.service;

import com.bkbwongo.core.ebaasa.base.service.CoreDTOService;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.CashFlowDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletGroupDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletTransactionDto;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
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

    @Autowired
    private UserManagementDTOMapperService userManagementDTOMapperService;

    public WalletGroupDto convertTWalletGroupToDTO(TWalletGroup tWalletGroup){
        var walletGroupDto =  modelMapper.map(tWalletGroup, WalletGroupDto.class);
        walletGroupDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(tWalletGroup.getCreatedBy()));
        if (tWalletGroup.getModifiedBy() != null) {
            walletGroupDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(tWalletGroup.getModifiedBy()));
        }
        return walletGroupDto;
    }

    public TWalletGroup convertDTOToTWalletGroup(WalletGroupDto walletGroupDto){
        var walletGroup = modelMapper.map(walletGroupDto, TWalletGroup.class);
        walletGroup.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(walletGroupDto.getCreatedBy()));
        if (walletGroupDto.getModifiedBy() != null){
            walletGroup.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(walletGroupDto.getModifiedBy()));
        }
        return walletGroup;
    }

    public WalletDto convertTWalletToDTO(TWallet tWallet){
        var walletDto = modelMapper.map(tWallet, WalletDto.class);
        walletDto.setWalletGroupDto(convertTWalletGroupToDTO(tWallet.getWalletGroup()));
        walletDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(tWallet.getCreatedBy()));

        if (tWallet.getModifiedBy() != null){
            walletDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(tWallet.getModifiedBy()));
        }

        if (tWallet.getActivatedBy() != null){
            walletDto.setActivatedBy(userManagementDTOMapperService.convertTUserToDTO(tWallet.getActivatedBy()));
        }

        if (tWallet.getClosedBy() != null){
            walletDto.setClosedBy(userManagementDTOMapperService.convertTUserToDTO(tWallet.getClosedBy()));
        }

        if (tWallet.getSuspendedBy() != null){
            walletDto.setSuspendedBy(userManagementDTOMapperService.convertTUserToDTO(tWallet.getSuspendedBy()));
        }
        return walletDto;
    }

    public TWallet convertDTOToTWallet(WalletDto walletDto){
        var wallet = modelMapper.map(walletDto, TWallet.class);
        wallet.setWalletGroup(convertDTOToTWalletGroup(walletDto.getWalletGroupDto()));

        if (walletDto.getModifiedBy() != null){
            wallet.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(walletDto.getModifiedBy()));
        }

        if (walletDto.getActivatedBy() != null){
            wallet.setActivatedBy(userManagementDTOMapperService.convertDTOToTUser(walletDto.getActivatedBy()));
        }

        if (walletDto.getClosedBy() != null){
            wallet.setClosedBy(userManagementDTOMapperService.convertDTOToTUser(walletDto.getClosedBy()));
        }

        if (walletDto.getSuspendedBy() != null){
            wallet.setSuspendedBy(userManagementDTOMapperService.convertDTOToTUser(walletDto.getSuspendedBy()));
        }
        return wallet;
    }

    public TWalletTransaction convertDTOToTWalletTransaction(WalletTransactionDto walletTransactionDto){
        var walletTransaction = modelMapper.map(walletTransactionDto, TWalletTransaction.class);
        walletTransaction.setWallet(convertDTOToTWallet(walletTransactionDto.getWallet()));
        return walletTransaction;
    }

    public WalletTransactionDto convertTWalletTransactionToDTO(TWalletTransaction tWalletTransaction){
        var walletTransactionDto = modelMapper.map(tWalletTransaction, WalletTransactionDto.class);
        walletTransactionDto.setWallet(convertTWalletToDTO(tWalletTransaction.getWallet()));
        return walletTransactionDto;
    }

    public TCashFlow convertDTOToTCashFlow(CashFlowDto cashFlowDto){
        var cashFlow = modelMapper.map(cashFlowDto, TCashFlow.class);
        cashFlow.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(cashFlowDto.getCreatedBy()));
        cashFlow.setFromWallet(convertDTOToTWallet(cashFlowDto.getFromWallet()));
        cashFlow.setToWallet(convertDTOToTWallet(cashFlowDto.getToWallet()));
        cashFlow.setFromWalletTransaction(convertDTOToTWalletTransaction(cashFlowDto.getFromWalletTransaction()));
        cashFlow.setToWalletTransaction(convertDTOToTWalletTransaction(cashFlowDto.getToWalletTransaction()));

        if (cashFlowDto.getApproveUser1() != null){
            cashFlow.setApproveUser1(userManagementDTOMapperService.convertDTOToTUser(cashFlowDto.getApproveUser1()));
        }

        if (cashFlowDto.getApproveUser2() != null){
            cashFlow.setApproveUser2(userManagementDTOMapperService.convertDTOToTUser(cashFlowDto.getApproveUser2()));
        }

        if (cashFlowDto.getModifiedBy() != null){
            cashFlow.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(cashFlowDto.getModifiedBy()));
        }

        if (cashFlowDto.getRejectedBy() != null){
            cashFlow.setRejectedBy(userManagementDTOMapperService.convertDTOToTUser(cashFlowDto.getRejectedBy()));
        }
        return cashFlow;
    }

    public CashFlowDto convertTCashFlowToDTO(TCashFlow tCashFlow){
        var cashFlowDto = modelMapper.map(tCashFlow, CashFlowDto.class);
        cashFlowDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(tCashFlow.getCreatedBy()));
        cashFlowDto.setFromWallet(convertTWalletToDTO(tCashFlow.getFromWallet()));
        cashFlowDto.setToWallet(convertTWalletToDTO(tCashFlow.getToWallet()));
        cashFlowDto.setFromWalletTransaction(convertTWalletTransactionToDTO(tCashFlow.getFromWalletTransaction()));
        cashFlowDto.setToWalletTransaction(convertTWalletTransactionToDTO(tCashFlow.getToWalletTransaction()));

        if (tCashFlow.getApproveUser1() != null){
            cashFlowDto.setApproveUser1(userManagementDTOMapperService.convertTUserToDTO(tCashFlow.getApproveUser1()));
        }

        if (tCashFlow.getApproveUser2() != null){
            cashFlowDto.setApproveUser2(userManagementDTOMapperService.convertTUserToDTO(tCashFlow.getApproveUser2()));
        }

        if (tCashFlow.getModifiedBy() != null){
            cashFlowDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(tCashFlow.getModifiedBy()));
        }

        if (tCashFlow.getRejectedBy() != null){
            cashFlowDto.setRejectedBy(userManagementDTOMapperService.convertTUserToDTO(tCashFlow.getRejectedBy()));
        }
        return cashFlowDto;
    }

}
