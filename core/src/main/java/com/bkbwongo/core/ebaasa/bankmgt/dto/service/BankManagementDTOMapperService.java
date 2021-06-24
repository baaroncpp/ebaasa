package com.bkbwongo.core.ebaasa.bankmgt.dto.service;

import com.bkbwongo.core.ebaasa.bankmgt.dto.BankAccountDto;
import com.bkbwongo.core.ebaasa.bankmgt.dto.BankDepositApprovalDto;
import com.bkbwongo.core.ebaasa.bankmgt.dto.BankDepositDto;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDeposit;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDepositApproval;
import com.bkbwongo.core.ebaasa.base.dto.service.CoreDTOService;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@Service
public class BankManagementDTOMapperService {

    private ModelMapper modelMapper;
    private CoreDTOService coreDTOService;
    private UserManagementDTOMapperService userManagementDTOMapperService;

    @Autowired
    public BankManagementDTOMapperService(ModelMapper modelMapper,
                                          CoreDTOService coreDTOService,
                                          UserManagementDTOMapperService userManagementDTOMapperService) {
        this.modelMapper = modelMapper;
        this.coreDTOService = coreDTOService;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
    }

    public TBankAccount convertDTOToTBankAccount(BankAccountDto bankAccountDto){
        var tBankAccount = modelMapper.map(bankAccountDto, TBankAccount.class);
        tBankAccount.setCountry(coreDTOService.convertDTOToTCountry(bankAccountDto.getCountry()));
        return tBankAccount;
    }

    public BankAccountDto convertTBankAccountToDTO(TBankAccount tBankAccount){
        var bankAccountDto = modelMapper.map(tBankAccount, BankAccountDto.class);
        bankAccountDto.setCountry(coreDTOService.convertTCountryToDTO(tBankAccount.getCountry()));
        return bankAccountDto;
    }

    public TBankDeposit convertDTOToTBankDeposit(BankDepositDto bankDepositDto){
        var bankDeposit = modelMapper.map(bankDepositDto, TBankDeposit.class);
        bankDeposit.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(bankDepositDto.getCreatedBy()));
        bankDeposit.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(bankDepositDto.getModifiedBy()));
        bankDeposit.setBank(convertDTOToTBankAccount(bankDepositDto.getBank()));
        bankDeposit.setCompany(userManagementDTOMapperService.convertDTOToTCompany(bankDepositDto.getCompany()));

        return bankDeposit;
    }

    public BankDepositDto convertTBankDepositToDTO(TBankDeposit bankDeposit){
        var bankDepositDto = modelMapper.map(bankDeposit, BankDepositDto.class);
        bankDepositDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(bankDeposit.getCreatedBy()));
        bankDepositDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(bankDeposit.getModifiedBy()));
        bankDepositDto.setBank(convertTBankAccountToDTO(bankDeposit.getBank()));
        bankDepositDto.setCompany(userManagementDTOMapperService.convertTCompanyToDTO(bankDeposit.getCompany()));

        return bankDepositDto;
    }

    public TBankDepositApproval convertDTOToTBankDepositApproval(BankDepositApprovalDto bankDepositApprovalDto){
        var bankDepositApproval = modelMapper.map(bankDepositApprovalDto, TBankDepositApproval.class);
        bankDepositApproval.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(bankDepositApprovalDto.getCreatedBy()));
        bankDepositApproval.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(bankDepositApprovalDto.getModifiedBy()));
        bankDepositApproval.setBankDeposit(convertDTOToTBankDeposit(bankDepositApprovalDto.getBankDeposit()));
        bankDepositApproval.setApprover1(userManagementDTOMapperService.convertDTOToTUser(bankDepositApprovalDto.getApprover1()));
        bankDepositApproval.setApprover2(userManagementDTOMapperService.convertDTOToTUser(bankDepositApprovalDto.getApprover2()));

        return bankDepositApproval;
    }

    public BankDepositApprovalDto convertTBankDepositApprovalToDTO(TBankDepositApproval bankDepositApproval){
        var bankDepositApprovalDto = modelMapper.map(bankDepositApproval, BankDepositApprovalDto.class);
        bankDepositApprovalDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(bankDepositApproval.getCreatedBy()));
        bankDepositApprovalDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(bankDepositApproval.getModifiedBy()));
        bankDepositApprovalDto.setBankDeposit(convertTBankDepositToDTO(bankDepositApproval.getBankDeposit()));
        bankDepositApprovalDto.setApprover1(userManagementDTOMapperService.convertTUserToDTO(bankDepositApproval.getApprover1()));
        bankDepositApprovalDto.setApprover2(userManagementDTOMapperService.convertTUserToDTO(bankDepositApproval.getApprover2()));

        return bankDepositApprovalDto;
    }
}
