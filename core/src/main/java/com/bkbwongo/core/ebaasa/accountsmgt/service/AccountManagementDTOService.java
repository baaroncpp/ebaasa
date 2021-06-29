package com.bkbwongo.core.ebaasa.accountsmgt.service;

import com.bkbwongo.core.ebaasa.bankmgt.dto.service.BankManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.smsmgt.dto.service.SmsManagementDTOService;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.service.WalletManagementDTOMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
@Service
public class AccountManagementDTOService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserManagementDTOMapperService userManagementDTOMapperService;

    @Autowired
    private BankManagementDTOMapperService bankManagementDTOMapperService;

    @Autowired
    private WalletManagementDTOMapperService walletManagementDTOMapperService;

    @Autowired
    private SmsManagementDTOService smsManagementDTOService;

}
