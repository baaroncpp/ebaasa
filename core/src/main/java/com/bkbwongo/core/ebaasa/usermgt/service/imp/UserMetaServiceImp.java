package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserMetaDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserMeta;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserMetaRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.UserMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserMetaServiceImp implements UserMetaService {

    @Autowired
    private AuditService auditService;

    private TUserMetaRepository tUserMetaRepository;
    private TUserRepository tUserRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;

    private static final String ID_NOT_DEFINED= "User ID not defined";

    @Autowired
    public UserMetaServiceImp(TUserMetaRepository tUserMetaRepository,
                              TUserRepository tUserRepository,
                              UserManagementDTOMapperService userManagementDTOMapperService) {
        this.tUserMetaRepository = tUserMetaRepository;
        this.tUserRepository = tUserRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
    }

    @Override
    public Optional<TUserMeta> addUserMeta(UserMetaDto userMetaDto) {

        userMetaDto.validate();

        tUserRepository.findById(userMetaDto.getUserId()).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, userMetaDto.getUserId()))
        );

        Optional<TUserMeta> userMeta = tUserMetaRepository.findByUserId(userMetaDto.getUserId());
        if(userMeta.isPresent()){
            throw new BadRequestException("UserMeta already exists");
        }

        var result = userManagementDTOMapperService.convertDTOToTUserMeta(userMetaDto);
        auditService.stampAuditedEntity(result);

        return Optional.of(
                tUserMetaRepository.save(result)
        );
    }

    @Override
    public Optional<TUserMeta> updateUserMeta(UserMetaDto userMetaDto) {

        userMetaDto.validate();

        tUserMetaRepository.findById(userMetaDto.getId())
                .orElseThrow(
                        () -> new BadRequestException(ErrorMessageConstants.ID_NOT_FOUND, userMetaDto.getId())
                );

        var result = userManagementDTOMapperService.convertDTOToTUserMeta(userMetaDto);
        auditService.stampAuditedEntity(result);

        return Optional.of(tUserMetaRepository.save(result));
    }

    @Override
    public Optional<TUserMeta> getUserMetaById(Long id) {
        Validate.notNull(id, ID_NOT_DEFINED);
        var userMeta = tUserMetaRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, id))
                );
        return Optional.of(userMeta);
    }

    @Override
    public Optional<TUserMeta> getUserMetaByUserId(Long id) {
        Validate.notNull(id, ID_NOT_DEFINED);
        var userMeta = tUserMetaRepository.findByUserId(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, id))
                );
        return Optional.of(userMeta);
    }
}
