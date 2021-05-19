package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserMetaDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserMeta;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/05/2021
 * @project ebaasa-sms
 */
public interface UserService {
    Optional<TUser> addUser(UserDto userDto);
    Optional<TUser> getUserById(Long id);
    Optional<TUser> getUserByUsername(String username);
    List<TUser> getAllUsers(Pageable pageable);
}
