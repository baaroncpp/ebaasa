package com.bkbwongo.core.ebaasa.security.service;

import com.bkbwongo.core.ebaasa.auth.EbaasaLoginUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private EbaasaLoginUserDAO ebaasaLoginUserDAO;

    @Autowired
    public EbaasaUserDetailsService(EbaasaLoginUserDAO ebaasaLoginUserDAO) {
        this.ebaasaLoginUserDAO = ebaasaLoginUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ebaasaLoginUserDAO
                .selectLoginUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found"));
    }
}
