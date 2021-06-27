package com.bkbwongo.core.ebaasa.security.service;

import com.bkbwongo.core.ebaasa.security.auth.EbaasaLoginUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
@Service
public class EbaasaUserDetailsService implements UserDetailsService {

    @Autowired
    private EbaasaLoginUserDAO ebaasaLoginUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ebaasaLoginUserDAO
                .selectLoginUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
