package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/05/2021
 * @project ebaasa-sms
 */
public interface UserMgtService {
    Optional<TUser> addUser(TUser tUser);
    Optional<TUser> getUserById(Long id);
}
