package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.CodeGenerator;
import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.service.WalletManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletGroupRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 12/06/2021
 * @project ebaasa-sms
 */
@Service
public class WalletServiceImp implements WalletService {

    private TWalletGroupRepository walletGroupRepository;
    private TWalletRepository walletRepository;
    private WalletManagementDTOMapperService walletManagementDTOMapperService;
    private AuditService auditService;

    @Autowired
    public WalletServiceImp(TWalletGroupRepository walletGroupRepository,
                            TWalletRepository walletRepository,
                            WalletManagementDTOMapperService walletManagementDTOMapperService,
                            AuditService auditService) {
        this.walletGroupRepository = walletGroupRepository;
        this.walletRepository = walletRepository;
        this.walletManagementDTOMapperService = walletManagementDTOMapperService;
        this.auditService = auditService;
    }

    @Override
    public Optional<TWallet> createWallet(WalletDto walletDto) {

        walletDto.validate();

        walletGroupRepository.findById(walletDto.getWalletGroupDto().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("WalletGroup with ID: %s does not exist", walletDto.getWalletGroupDto().getId()))
                );

        var codeExists = true;
        var walletAccountCode = "";

        while (codeExists) {
            walletAccountCode = CodeGenerator.numericStringCode(12);
            if(walletRepository.findByCode(walletAccountCode).isPresent()){
                codeExists = true;
            }else{
                codeExists = false;
            }
        }

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        wallet.setCode(walletAccountCode);
        auditService.stampAuditedEntity(wallet);

        return Optional.of(walletRepository.save(wallet));
    }

    @Override
    public Optional<TWallet> updateWallet(WalletDto walletDto) {

        Validate.notNull(walletDto.getId(), String.format(ErrorMessageConstants.NULL_VALUE,"ID"));
        walletDto.validate();

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        auditService.stampAuditedEntity(wallet);
        return Optional.of(walletRepository.save(wallet));
    }

    @Override
    public Optional<TWallet> getWalletById(Long id) {
        var wallet = walletRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, id))
                );
        return Optional.of(wallet);
    }

    @Override
    public List<TWallet> getWalletsByGroup(Long walletGroupId, Pageable pageable) {
        var walletGroup = walletGroupRepository.findById(walletGroupId)
                .orElseThrow(
                        () -> new BadRequestException(String.format("WalletGroup with ID: %s does not exist", walletGroupId))
                );
        return walletRepository.findByWalletGroup(pageable, walletGroup).getContent();
    }

    @Override
    public List<TWallet> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<TWallet> activateWallet(WalletDto walletDto) {

        walletDto.validate();
        Validate.notNull(walletDto.getId(), String.format(ErrorMessageConstants.NULL_VALUE,"ID"));

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        if(Boolean.TRUE.equals(wallet.getActive())){
            throw new BadRequestException("Wallet account is already active");
        }

        auditService.stampAuditedEntity(wallet);
        var activatingUser = wallet.getModifiedBy();

        wallet.setActive(Boolean.TRUE);
        wallet.setActivateOn(DateTimeUtil.getCurrentUTCTime());
        wallet.setActivatedBy(activatingUser);
        wallet.setClosed(Boolean.FALSE);
        wallet.setSuspended(Boolean.FALSE);

        return Optional.of(wallet);
    }

    @Override
    public Optional<TWallet> suspendWallet(WalletDto walletDto) {

        walletDto.validate();
        Validate.notNull(walletDto.getId(), String.format(ErrorMessageConstants.NULL_VALUE,"ID"));

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        if(Boolean.TRUE.equals(wallet.getSuspended())){
            throw new BadRequestException("Wallet account is already suspended");
        }

        auditService.stampAuditedEntity(wallet);
        var suspendingUser = wallet.getModifiedBy();

        wallet.setActive(Boolean.FALSE);
        wallet.setSuspended(Boolean.TRUE);
        wallet.setSuspendedBy(suspendingUser);
        wallet.setSuspendedOn(DateTimeUtil.getCurrentUTCTime());

        return Optional.of(wallet);
    }

    @Override
    public Optional<TWallet> closeWallet(WalletDto walletDto) {

        walletDto.validate();
        Validate.notNull(walletDto.getId(), String.format(ErrorMessageConstants.NULL_VALUE,"ID"));

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_NOT_FOUND, walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);

        Validate.isTrue(wallet.getAvailableBalance().compareTo(BigDecimal.ZERO) == 0,"Account balance has to be 0 to close account");
        Validate.isTrue(!wallet.getAssigned(),"Account that has been assigned cannot be closed");


        if(Boolean.TRUE.equals(wallet.getClosed())){
            throw new BadRequestException("Wallet account is already closed");
        }

        auditService.stampAuditedEntity(wallet);
        var closingUser = wallet.getModifiedBy();

        wallet.setActive(Boolean.FALSE);
        wallet.setClosed(Boolean.TRUE);
        wallet.setClosedBy(closingUser);
        wallet.setClosedOn(DateTimeUtil.getCurrentUTCTime());

        return Optional.of(wallet);
    }
}
