package com.bkbwongo.core.ebaasa.smsmgt.dto.service;

import com.bkbwongo.core.ebaasa.smsmgt.dto.SmsAccountDto;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
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

    public TSmsAccount convertDTOToTSmsAccount(SmsAccountDto smsAccountDto){
        var smsAccount = modelMapper.map(smsAccountDto, TSmsAccount.class);
        // TODO add other converts

        return smsAccount;
    }

    public SmsAccountDto convertTSmsAccountToDTO(TSmsAccount smsAccount){
        var smsAccountDto = modelMapper.map(smsAccount, SmsAccountDto.class);
        // TODO add other converts

        return smsAccountDto;
    }
}
