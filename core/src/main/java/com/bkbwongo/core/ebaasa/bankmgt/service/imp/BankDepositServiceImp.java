package com.bkbwongo.core.ebaasa.bankmgt.service.imp;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.bankmgt.dto.BankDepositDto;
import com.bkbwongo.core.ebaasa.bankmgt.dto.service.BankManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDeposit;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankDepositApproval;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankAccountRepository;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankDepositApprovalRepository;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankDepositRepository;
import com.bkbwongo.core.ebaasa.bankmgt.service.BankDepositService;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 24/06/2021
 * @project ebaasa-sms
 */
@Service
public class BankDepositServiceImp implements BankDepositService {

    private BankManagementDTOMapperService bankManagementDTOMapperService;
    private TBankDepositRepository bankDepositRepository;
    private TBankDepositApprovalRepository bankDepositApprovalRepository;
    private TBankAccountRepository bankAccountRepository;

    @Autowired
    public BankDepositServiceImp(BankManagementDTOMapperService bankManagementDTOMapperService,
                                 TBankDepositRepository bankDepositRepository,
                                 TBankDepositApprovalRepository bankDepositApprovalRepository,
                                 TBankAccountRepository bankAccountRepository) {
        this.bankManagementDTOMapperService = bankManagementDTOMapperService;
        this.bankDepositRepository = bankDepositRepository;
        this.bankDepositApprovalRepository = bankDepositApprovalRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Optional<TBankDeposit> initialDeposit(BankDepositDto bankDepositDto) {

        bankDepositDto.validate();

        var bankAccount = bankAccountRepository.findById(bankDepositDto.getBank().getId());
        Validate.isPresent(bankAccount, "Bank with ID %s not found in the system");

        var bankDeposit = bankManagementDTOMapperService.convertDTOToTBankDeposit(bankDepositDto);
        bankDeposit.setBank(bankAccount.get());
        bankDeposit.setStatus(TransactionStatusEnum.PENDING);

        //auditedService required

        bankDepositRepository.save(bankDeposit);

        TBankDepositApproval approvalRequest = new TBankDepositApproval();
        approvalRequest.setBankDeposit(bankDeposit);
        approvalRequest.setStatus(ApprovalEnum.PENDING);
        approvalRequest.setApprovalCount(0);
        approvalRequest.setCreatedOn(new Date());
        approvalRequest.setCreatedBy(bankDeposit.getCreatedBy());

        //auditedService required

        bankDepositApprovalRepository.save(approvalRequest);
        
        return Optional.of(bankDeposit);
    }

    @Override
    public Boolean firstDepositApproval(Long id, TUser approveUser) {
        return null;
    }

    @Override
    public Boolean secondDepositApproval(Long id, TUser approveUser) {
        return null;
    }

    @Override
    public Optional<TBankDeposit> getDepositById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TBankDeposit> getBankDepositsByBank(Long bankId, Pageable pageable) {
        return null;
    }

    @Override
    public List<TBankDeposit> getBankDeposits(Pageable pageable) {
        return null;
    }
}
