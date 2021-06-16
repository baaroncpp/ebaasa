package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.TransactionStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.TransactionTypeEnum;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
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
 * @created on 16/06/2021 happy birthday
 * @project ebaasa-sms
 */
@Service
public class CashFlowServiceImp implements CashFlowService {

    private WalletManagementDTOMapperService walletManagementDTOMapperService;
    private TCashFlowRepository cashFlowRepository;
    private TWalletTransactionRepository walletTransactionRepository;
    private TWalletRepository walletRepository;

    @Autowired
    public CashFlowServiceImp(WalletManagementDTOMapperService walletManagementDTOMapperService,
                              TCashFlowRepository cashFlowRepository,
                              TWalletTransactionRepository walletTransactionRepository,
                              TWalletRepository walletRepository) {
        this.walletManagementDTOMapperService = walletManagementDTOMapperService;
        this.cashFlowRepository = cashFlowRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    @Override
    public Optional<TCashFlow> initiateCashFlow(CashFlowDto cashFlowDto, TUser user) {

        Validate.notNull(cashFlowDto.getFromWallet(), String.format(ErrorMessageConstants.NULL_OBJECT_VALUE, "wallet"));
        Validate.notNull(cashFlowDto.getToWallet(), String.format(ErrorMessageConstants.NULL_OBJECT_VALUE, "wallet"));
        Validate.notNull(cashFlowDto.getFlowType(), "cash flow type not defined");

        var createdOn = new Date();

        TCashFlow tCashFlow = walletManagementDTOMapperService.convertDTOToTCashFlow(cashFlowDto);
        tCashFlow.setCreatedOn(createdOn);
        tCashFlow.setCreatedBy(user);

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
                .createdOn(createdOn)
                .externalTransactionId(tCashFlow.getExternalReference())
                .transactionType(TransactionTypeEnum.WALLET_WITHDRAW)
                .balanceBefore(tCashFlow.getFromWallet().getAvailableBalance())
                .balanceAfter(tCashFlow.getFromWallet().getAvailableBalance().subtract(tCashFlow.getAmount()))
                .wallet(fromWallet)
                .transactionStatus(TransactionStatusEnum.PENDING)
                .nonReversal(Boolean.FALSE)
                .statusDescription(tCashFlow.getNote())
                .build();

        //to wallet transaction
        TWalletTransaction toWalletTransaction = TWalletTransaction.builder()
                .createdOn(createdOn)
                .externalTransactionId(tCashFlow.getExternalReference())
                .transactionType(TransactionTypeEnum.WALLET_DEPOSIT)
                .balanceBefore(tCashFlow.getToWallet().getAvailableBalance())
                .balanceAfter(tCashFlow.getToWallet().getAvailableBalance().add(tCashFlow.getAmount()))
                .wallet(toWallet)
                .transactionStatus(TransactionStatusEnum.PENDING)
                .nonReversal(Boolean.FALSE)
                .statusDescription(tCashFlow.getNote())
                .build();

        tCashFlow.setToWallet(toWallet);
        tCashFlow.setFromWallet(fromWallet);
        tCashFlow.setFromWalletTransaction(fromWalletTransaction);
        tCashFlow.setToWalletTransaction(toWalletTransaction);
        tCashFlow.setFirstApproved(Boolean.FALSE);
        tCashFlow.setSecondApproved(Boolean.FALSE);

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
    public Optional<TCashFlow> cashFlowApproval1(Long id, TUser user) {

        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));
        var now = new Date();

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );

        if (cashFlow.getFirstApproved()){
            throw new BadRequestException(ErrorMessageConstants.FIRST_CASH_FLOW_APPROVED);
        }

        cashFlow.setFirstApproved(Boolean.TRUE);
        cashFlow.setFirstApprovedOn(now);
        cashFlow.setApproveUser1(user);

        var fromWalletTransaction = walletTransactionRepository.findById(cashFlow.getFromWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getFromWalletTransaction().getId()))
                );

        var toWalletTransaction = walletTransactionRepository.findById(cashFlow.getToWalletTransaction().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_TRANSACTION_NOT_FOUND, cashFlow.getToWalletTransaction().getId()))
                );

        fromWalletTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFUL);
        fromWalletTransaction.setModifiedOn(now);

        toWalletTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFUL);
        toWalletTransaction.setModifiedOn(now);

        walletTransactionRepository.save(fromWalletTransaction);
        walletTransactionRepository.save(toWalletTransaction);

        return Optional.of(cashFlowRepository.save(cashFlow));
    }

    @Transactional
    @Override
    public Optional<TCashFlow> cashFlowApproval2(Long id, TUser user) {

        Validate.notNull(id, String.format(ErrorMessageConstants.NULL_VALUE, "id"));

        var cashFlow = cashFlowRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.CASH_FLOW_NOT_FOUND, id))
        );

        if (!cashFlow.getFirstApproved()){
            throw new BadRequestException(ErrorMessageConstants.FIRST_CASH_FLOW_NOT_APPROVED);
        }

        if (cashFlow.getSecondApproved()){
            throw new BadRequestException(ErrorMessageConstants.SECOND_CASH_FLOW_APPROVED);
        }

        cashFlow.setSecondApproved(Boolean.TRUE);
        cashFlow.setSecondApprovedOn(new Date());
        cashFlow.setApproveUser2(user);

        return Optional.empty();
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
