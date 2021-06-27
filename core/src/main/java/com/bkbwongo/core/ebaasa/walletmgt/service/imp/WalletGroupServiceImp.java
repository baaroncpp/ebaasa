package com.bkbwongo.core.ebaasa.walletmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletGroupDto;
import com.bkbwongo.core.ebaasa.walletmgt.dto.service.WalletManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;
import com.bkbwongo.core.ebaasa.walletmgt.repository.TWalletGroupRepository;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Service
public class WalletGroupServiceImp implements WalletGroupService {

    private WalletManagementDTOMapperService walletManagementDTOMapperService;
    private TWalletGroupRepository walletGroupRepository;
    private AuditService auditService;

    @Autowired
    public WalletGroupServiceImp(WalletManagementDTOMapperService walletManagementDTOMapperService,
                                 TWalletGroupRepository walletGroupRepository,
                                 AuditService auditService) {
        this.walletManagementDTOMapperService = walletManagementDTOMapperService;
        this.walletGroupRepository = walletGroupRepository;
        this.auditService = auditService;
    }

    @Override
    public Optional<TWalletGroup> createWalletGroup(WalletGroupDto walletGroupDto) {

        walletGroupDto.validate();

        var walletGroup = walletManagementDTOMapperService.convertDTOToTWalletGroup(walletGroupDto);
        auditService.stampAuditedEntity(walletGroup);

        return Optional.of(walletGroupRepository.save(walletGroup));
    }

    @Override
    public Optional<TWalletGroup> updateWalletGroup(WalletGroupDto walletGroupDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TWalletGroup> getWalletGroupById(Long id) {
        Validate.notNull(id, "NULL ID value");

        var walletGroup = walletGroupRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.WALLET_GROUP_NOT_FOUND, id))
        );
        return Optional.of(walletGroup);
    }

    @Override
    public Optional<TWalletGroup> getWalletGroupByName(String name) {
        Validate.notEmpty(name, "Null walletGroup name");

        var walletGroup = walletGroupRepository.findByName(name)
                .orElseThrow(
                        () -> new BadRequestException("WalletGroup not found")
                );
        return Optional.of(walletGroup);
    }

    @Override
    public List<TWalletGroup> getAllWalletGroups() {
        var walletGroups = walletGroupRepository.findAll();
        if(walletGroups.isEmpty()){
            throw new BadRequestException("No wallet groups");
        }
        return walletGroups;
    }
}
