package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.CashFlowDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.service.WalletManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TCashFlow;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletTransaction;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TCashFlowRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletTransactionRepository;
import com.bkbwongo.core.ebaasa.walletmgt.service.CashFlowService;
import com.bkbwongo.core.ebaasa.walletmgt.utitlities.WalletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
@Service
public class CashFlowServiceImp implements CashFlowService {

    private WalletManagementDTOMapperService walletManagementDTOMapperService;
    private TCashFlowRepository cashFlowRepository;
    private TWalletTransactionRepository walletTransactionRepository;
    private TWalletRepository walletRepository;
    private AuditService auditService;

    @Autowired
    public CashFlowServiceImp(WalletManagementDTOMapperService walletManagementDTOMapperService,
                              TCashFlowRepository cashFlowRepository,
                              TWalletTransactionRepository walletTransactionRepository,
                              TWalletRepository walletRepository,
                              AuditService auditService) {
        this.walletManagementDTOMapperService = walletManagementDTOMapperService;
        this.cashFlowRepository = cashFlowRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletRepository = walletRepository;
        this.auditService = auditService;
    }

    @Transactional
    @Override
    public Optional<TCashFlow> initiateCashFlow(CashFlowDto cashFlowDto) {

        cashFlowDto.validate();

        var tCashFlow = walletManagementDTOMapperService.convertDTOToTCashFlow(cashFlowDto);

        var fromWallet = walletRepository.findById(tCashFlow.getFromWallet().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, tCashFlow.getFromWallet().getId()))
                );

        var toWallet = walletRepository.findById(tCashFlow.getToWallet().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, tCashFlow.getToWallet().getId()))
                );

        if (fromWallet.getId().equals(toWallet.getId())){
            throw new BadRequestException(ErrorMessageConstants.SAME_WALLET_ACCOUNT);
        }

        //check if wallets can transact
        WalletUtilities.checkThatAccountCanTransact(tCashFlow.getFromWallet());
        WalletUtilities.checkThatAccountCanTransact(tCashFlow.getToWallet());

        //from wallet transaction
        TWalletTransaction fromWalletTransaction = TWalletTransaction.builder()
                .externalTransactionId(tCashFlow.getExternalReference())
                .transactionType(TransactionTypeEnum.WALLET_WITHDRAW)
                .balanceBefore(tCashFlow.getFromWallet().getAvailableBalance())
                .balanceAfter(tCashFlow.getFromWallet().getAvailableBalance().subtract(tCashFlow.getAmount()))
                .wallet(fromWallet)
                .transactionStatus(TransactionStatusEnum.PENDING)
                .nonReversal(Boolean.FALSE)
                .statusDescription(tCashFlow.getNote())
                .build();

        auditService.stampAuditedEntity(fromWallet);

        //to wallet transaction
        TWalletTransaction toWalletTransaction = TWalletTransaction.builder()
                .externalTransactionId(tCashFlow.getExternalReference())
                .transactionType(TransactionTypeEnum.WALLET_DEPOSIT)
                .balanceBefore(tCashFlow.getToWallet().getAvailableBalance())
                .balanceAfter(tCashFlow.getToWallet().getAvailableBalance().add(tCashFlow.getAmount()))
                .wallet(toWallet)
                .transactionStatus(TransactionStatusEnum.PENDING)
                .nonReversal(Boolean.FALSE)
                .statusDescription(tCashFlow.getNote())
                .build();

        auditService.stampAuditedEntity(toWallet);

        tCashFlow.setToWallet(toWallet);
        tCashFlow.setFromWallet(fromWallet);
        tCashFlow.setFromWalletTransaction(fromWalletTransaction);
        tCashFlow.setToWalletTransaction(toWalletTransaction);
        tCashFlow.setFirstApproved(Boolean.FALSE);
        tCashFlow.setSecondApproved(Boolean.FALSE);
        tCashFlow.setRejected(Boolean.FALSE);
        auditService.stampAuditedEntity(tCashFlow);

        walletTransactionRepository.save(fromWalletTransaction);
        walletTransactionRepository.save(toWalletTransaction);

        return Optional.of(cashFlowRepository.save(tCashFlow));
    }

    @Override
    public Optional<TCashFlow> automaticCashFlow(CashFlowDto cashFlowDto) {
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<TCashFlow> cashFlowApproval1(Long id) {

        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );

        if (Boolean.TRUE.equals(cashFlow.getFirstApproved())){
            throw new BadRequestException(ErrorMessageConstants.FIRST_CASH_FLOW_APPROVED);
        }

        if (Boolean.TRUE.equals(cashFlow.getRejected())){
            throw new BadRequestException(ErrorMessageConstants.CASH_FLOW_REJECTED);
        }

        auditService.stampAuditedEntity(cashFlow);
        var approveUser = cashFlow.getModifiedBy();

        cashFlow.setFirstApproved(Boolean.TRUE);
        cashFlow.setFirstApprovedOn(new Date());
        cashFlow.setApproveUser1(approveUser);

        return Optional.of(cashFlowRepository.save(cashFlow));
    }

    @Transactional
    @Override
    public Optional<TCashFlow> cashFlowApproval2(Long id) {

        var now = new Date();
        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );

        var fromWalletTransaction = walletTransactionRepository.findById(cashFlow.getFromWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getFromWalletTransaction().getId()))
                );

        var toWalletTransaction = walletTransactionRepository.findById(cashFlow.getToWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getToWalletTransaction().getId()))
                );

        if (Boolean.FALSE.equals(cashFlow.getFirstApproved())){
            throw new BadRequestException(ErrorMessageConstants.FIRST_CASH_FLOW_NOT_APPROVED);
        }

        if (Boolean.TRUE.equals(cashFlow.getSecondApproved())){
            throw new BadRequestException(ErrorMessageConstants.SECOND_CASH_FLOW_APPROVED);
        }

        fromWalletTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFUL);
        fromWalletTransaction.setModifiedOn(now);
        fromWalletTransaction.setStatusDescription(ErrorMessageConstants.CASH_FLOW_APPROVED_SUCCESSFULLY);

        toWalletTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFUL);
        toWalletTransaction.setStatusDescription(ErrorMessageConstants.CASH_FLOW_APPROVED_SUCCESSFULLY);

        walletTransactionRepository.save(fromWalletTransaction);
        walletTransactionRepository.save(toWalletTransaction);

        auditService.stampAuditedEntity(cashFlow);
        var approveUser = cashFlow.getModifiedBy();

        cashFlow.setSecondApproved(Boolean.TRUE);
        cashFlow.setSecondApprovedOn(now);
        cashFlow.setApproveUser2(approveUser);
        cashFlow.setRejected(Boolean.FALSE);

        return Optional.of(cashFlowRepository.save(cashFlow));
    }

    @Override
    public Optional<TCashFlow> rejectCashFlow(Long id) {
        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));
        var now = new Date();

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );

        var fromWalletTransaction = walletTransactionRepository.findById(cashFlow.getFromWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getFromWalletTransaction().getId()))
                );

        var toWalletTransaction = walletTransactionRepository.findById(cashFlow.getToWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getToWalletTransaction().getId()))
                );

        fromWalletTransaction.setTransactionStatus(TransactionStatusEnum.FAILED);
        fromWalletTransaction.setModifiedOn(now);
        fromWalletTransaction.setStatusDescription(ErrorMessageConstants.CASH_FLOW_REJECTED);

        toWalletTransaction.setTransactionStatus(TransactionStatusEnum.FAILED);
        toWalletTransaction.setModifiedOn(now);
        toWalletTransaction.setStatusDescription(ErrorMessageConstants.CASH_FLOW_REJECTED);

        walletTransactionRepository.save(fromWalletTransaction);
        walletTransactionRepository.save(toWalletTransaction);

        auditService.stampAuditedEntity(cashFlow);
        var rejectUser = cashFlow.getModifiedBy();

        cashFlow.setSecondApproved(Boolean.TRUE);
        cashFlow.setFirstApproved(Boolean.TRUE);
        cashFlow.setRejectedBy(rejectUser);
        cashFlow.setRejectedOn(now);
        cashFlow.setRejected(Boolean.TRUE);

        return Optional.of(cashFlowRepository.save(cashFlow));
    }

    @Override
    public Optional<TCashFlow> getCashFlowById(Long id) {
        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );
        return Optional.of(cashFlow);
    }

    @Override
    public List<TCashFlow> getDepositCashFlowsByWalletId(Long walletId, Pageable pageable) {
        Validate.notNull(walletId, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId))
        );

        return cashFlowRepository.findByToWalletTransaction(wallet, pageable).getContent();
    }

    @Override
    public List<TCashFlow> getWithDrawCashFlowsByWalletId(Long walletId, Pageable pageable) {
        Validate.notNull(walletId, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletId))
        );

        return cashFlowRepository.findByFromWalletTransaction(wallet, pageable).getContent();
    }

    @Override
    public List<TCashFlow> getCashFlows(Pageable pageable) {
        return cashFlowRepository.findAll(pageable).getContent();
    }
}
