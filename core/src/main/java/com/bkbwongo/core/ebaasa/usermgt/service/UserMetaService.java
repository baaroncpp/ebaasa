package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.UserMetaDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserMeta;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 21/05/2021
 * @project ebaasa-sms
 */
public interface UserMetaService {
    Optional<TUserMeta> addUserMeta(UserMetaDto userMetaDto);
    Optional<TUserMeta> updateUserMeta(UserMetaDto userMetaDto);
    Optional<TUserMeta> getUserMetaById(Long id);
    Optional<TUserMeta> getUserMetaByUserId(Long id);
}
