package com.bkbwongo.core.ebaasa.security;

import com.bkbwongo.core.ebaasa.security.service.ClientUserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author bkaaron
 * @created on 04/05/2021
 * @project ebaasa-sms
 */

@Configuration
@EnableAuthorizationServer
public class OAuthAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new ClientUserDetailsServiceImpl());
    }
}
