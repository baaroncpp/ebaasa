package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.CodeGenerator;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.service.WalletManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletGroupRepository;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletRepository;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Date;
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

    @Autowired
    public WalletServiceImp(TWalletGroupRepository walletGroupRepository,
                            TWalletRepository walletRepository,
                            WalletManagementDTOMapperService walletManagementDTOMapperService) {
        this.walletGroupRepository = walletGroupRepository;
        this.walletRepository = walletRepository;
        this.walletManagementDTOMapperService = walletManagementDTOMapperService;
    }

    @Override
    public Optional<TWallet> createWallet(WalletDto walletDto, TUser user) {
        Validate.notNull(walletDto, "Null wallet object");
        Validate.notEmpty(walletDto.getName(),"Name value not defined");
        Validate.notNull(walletDto.getWalletGroupDto(), "WalletGroup not defined");

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
        wallet.setCreatedOn(new Date());
        wallet.setCreatedBy(user);

        return Optional.of(walletRepository.save(wallet));
    }

    @Override
    public Optional<TWallet> updateWallet(WalletDto walletDto) {

        Validate.notNull(walletDto, "Null wallet object");
        Validate.notNull(walletDto.getId(), "Null ID value");
        Validate.notEmpty(walletDto.getName(),"Name value not defined");
        Validate.notNull(walletDto.getWalletGroupDto(), "WalletGroup not defined");

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s does not exist", walletDto.getId()))
                );
        return Optional.of(walletRepository.save(
                walletManagementDTOMapperService.convertDTOToTWallet(walletDto)
        ));
    }

    @Override
    public Optional<TWallet> getWalletById(Long id) {
        var wallet = walletRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s does not exist", id))
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
    public Optional<TWallet> activateWallet(WalletDto walletDto, TUser user) {

        Validate.notNull(walletDto, "Null wallet object");
        Validate.notNull(walletDto.getId(), "Wallet ID not defined");

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s does not exist", walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        if(wallet.getActive()){
            throw new BadRequestException("Wallet account is already active");
        }

        wallet.setActive(Boolean.TRUE);
        wallet.setActivateOn(new Date());
        wallet.setActivatedBy(user);
        wallet.setClosed(Boolean.FALSE);
        wallet.setSuspended(Boolean.FALSE);

        return Optional.of(wallet);
    }

    @Override
    public Optional<TWallet> suspendWallet(WalletDto walletDto, TUser user) {

        Validate.notNull(walletDto, "Null wallet object");
        Validate.notNull(walletDto.getId(), "Wallet ID not defined");

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s does not exist", walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        if(wallet.getSuspended()){
            throw new BadRequestException("Wallet account is already suspended");
        }

        wallet.setActive(Boolean.FALSE);
        wallet.setSuspended(Boolean.TRUE);
        wallet.setSuspendedBy(user);
        wallet.setSuspendedOn(new Date());

        return Optional.of(wallet);
    }

    @Override
    public Optional<TWallet> closeWallet(WalletDto walletDto, TUser user) {

        Validate.notNull(walletDto, "Null wallet object");
        Validate.notNull(walletDto.getId(), "Wallet ID not defined");

        walletRepository.findById(walletDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Wallet with ID: %s does not exist", walletDto.getId()))
                );

        var wallet = walletManagementDTOMapperService.convertDTOToTWallet(walletDto);
        if(wallet.getClosed()){
            throw new BadRequestException("Wallet account is already closed");
        }

        wallet.setActive(Boolean.FALSE);
        wallet.setClosed(Boolean.TRUE);
        wallet.setClosedBy(user);
        wallet.setClosedOn(new Date());

        return Optional.of(wallet);
    }
}
