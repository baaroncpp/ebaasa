package com.bkbwongo.core.ebaasa.smsmgt.dto.service;

import com.bkbwongo.core.ebaasa.smsmgt.dto.*;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.*;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Service
public class SmsManagementDTOService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserManagementDTOMapperService userManagementDTOMapperService;

    public TSmsAccount convertDTOToTSmsAccount(SmsAccountDto smsAccountDto){
        var smsAccount = modelMapper.map(smsAccountDto, TSmsAccount.class);
        smsAccount.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountDto.getCreatedBy()));
        smsAccount.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountDto.getModifiedBy()));
        smsAccount.setSmsAccountType(convertDTOToTSmsAccountGroup(smsAccountDto.getSmsAccountType()));
        smsAccount.setClosedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountDto.getClosedBy()));
        smsAccount.setActivatedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountDto.getActivatedBy()));
        smsAccount.setAssignedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountDto.getAssignedBy()));

        return smsAccount;
    }

    public SmsAccountDto convertTSmsAccountToDTO(TSmsAccount smsAccount){
        var smsAccountDto = modelMapper.map(smsAccount, SmsAccountDto.class);
        smsAccountDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccount.getCreatedBy()));
        smsAccountDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccount.getModifiedBy()));
        smsAccountDto.setSmsAccountType(convertTSmsAccountGroupToDTO(smsAccount.getSmsAccountType()));
        smsAccountDto.setClosedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccount.getClosedBy()));
        smsAccountDto.setActivatedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccount.getActivatedBy()));
        smsAccountDto.setAssignedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccount.getAssignedBy()));

        return smsAccountDto;
    }

    public TSmsAccountGroup convertDTOToTSmsAccountGroup(SmsAccountGroupDto smsAccountGroupDto){
        var smsAccountGroup = modelMapper.map(smsAccountGroupDto, TSmsAccountGroup.class);
        smsAccountGroup.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountGroupDto.getCreatedBy()));
        smsAccountGroup.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(smsAccountGroupDto.getModifiedBy()));

        return smsAccountGroup;
    }

    public SmsAccountGroupDto convertTSmsAccountGroupToDTO(TSmsAccountGroup smsAccountGroup){
        var smsAccountGroupDto = modelMapper.map(smsAccountGroup, SmsAccountGroupDto.class);
        smsAccountGroupDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccountGroup.getCreatedBy()));
        smsAccountGroupDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(smsAccountGroup.getModifiedBy()));

        return smsAccountGroupDto;
    }

    public TSms convertDTOToTSms(SmsDto smsDto){
        return modelMapper.map(smsDto, TSms.class);
    }

    public SmsDto convertTSmsToDTO(TSms sms){
        return modelMapper.map(sms, SmsDto.class);
    }

    public TSmsPackageType convertDTOToTSmsPackageType(SmsPackageTypeDto smsPackageTypeDto){
        var smsPackageType = modelMapper.map(smsPackageTypeDto, TSmsPackageType.class);
        smsPackageType.setActivatedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTypeDto.getActivatedBy()));
        smsPackageType.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTypeDto.getModifiedBy()));
        smsPackageType.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTypeDto.getCreatedBy()));
        smsPackageType.setClosedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTypeDto.getClosedBy()));

        return smsPackageType;
    }

    public SmsPackageTypeDto convertTSmsPackageTypeToDTO(TSmsPackageType smsPackageType){
        var smsPackageTypeDto = modelMapper.map(smsPackageType, SmsPackageTypeDto.class);
        smsPackageTypeDto.setActivatedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageType.getActivatedBy()));
        smsPackageTypeDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageType.getModifiedBy()));
        smsPackageTypeDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageType.getCreatedBy()));
        smsPackageTypeDto.setClosedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageType.getClosedBy()));

        return smsPackageTypeDto;
    }

    public TSmsPackage convertDTOToTSmsPackage(SmsPackageDto smsPackageDto){
        var smsPackage = modelMapper.map(smsPackageDto, TSmsPackage.class);
        smsPackage.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageDto.getCreatedBy()));
        smsPackage.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageDto.getModifiedBy()));
        smsPackage.setSmsAccount(convertDTOToTSmsAccount(smsPackageDto.getSmsAccount()));

        return smsPackage;
    }

    public SmsPackageDto convertTSmsPackageToDTO(TSmsPackage smsPackage){
        var smsPackageDto = modelMapper.map(smsPackage, SmsPackageDto.class);
        smsPackageDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackage.getCreatedBy()));
        smsPackageDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackage.getModifiedBy()));
        smsPackageDto.setSmsAccount(convertTSmsAccountToDTO(smsPackage.getSmsAccount()));

        return smsPackageDto;
    }

    public SmsPackageTransactionDto convertTSmsPackageTransactionToDTO(TSmsPackageTransaction smsPackageTransaction){
        var smsPackageTransactionDto = modelMapper.map(smsPackageTransaction, SmsPackageTransactionDto.class);
        smsPackageTransactionDto.setCreatedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageTransaction.getCreatedBy()));
        smsPackageTransactionDto.setModifiedBy(userManagementDTOMapperService.convertTUserToDTO(smsPackageTransaction.getModifiedBy()));
        smsPackageTransactionDto.setSmsAccount(convertTSmsAccountToDTO(smsPackageTransaction.getSmsAccount()));
        smsPackageTransactionDto.setSmsPackage(convertTSmsPackageToDTO(smsPackageTransaction.getSmsPackage()));

        return smsPackageTransactionDto;
    }

    public TSmsPackageTransaction convertDTOToTSmsPackageTransaction(SmsPackageTransactionDto smsPackageTransactionDto){
        var smsPackageTransaction = modelMapper.map(smsPackageTransactionDto, TSmsPackageTransaction.class);
        smsPackageTransaction.setCreatedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTransactionDto.getCreatedBy()));
        smsPackageTransaction.setModifiedBy(userManagementDTOMapperService.convertDTOToTUser(smsPackageTransactionDto.getModifiedBy()));
        smsPackageTransaction.setSmsAccount(convertDTOToTSmsAccount(smsPackageTransactionDto.getSmsAccount()));
        smsPackageTransaction.setSmsPackage(convertDTOToTSmsPackage(smsPackageTransactionDto.getSmsPackage()));

        return smsPackageTransaction;
    }
}
