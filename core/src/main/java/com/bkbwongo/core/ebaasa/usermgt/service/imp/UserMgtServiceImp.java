package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.repository.UserRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.UserMgtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserMgtServiceImp implements UserMgtService {

    private UserRepository userRepository;



    @Override
    public Optional<TUser> addUser(TUser tUser) {
        return Optional.empty();
    }

    @Override
    public Optional<TUser> getUserById(Long id) {
        return Optional.empty();
    }
}
