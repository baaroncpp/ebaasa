package com.bkbwongo.core.ebaasa.auth;

import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
public interface EbaasaLoginUserDAO {
    Optional<EbaasaLoginUser> selectLoginUserByUsername(String username);
}
