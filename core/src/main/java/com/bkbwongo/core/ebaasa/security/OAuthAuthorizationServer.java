package com.bkbwongo.core.ebaasa.security;

import com.bkbwongo.core.ebaasa.security.service.ClientUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author bkaaron
 * @created on 04/05/2021
 * @project ebaasa-sms
 */

//@Configuration
//@EnableAuthorizationServer
public class OAuthAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.certificate.password}")
    private String password;

    @Value("${jwt.certificate.private-key}")
    private String privateKey;

    @Value("${jwt.certificate.alias}")
    private String alias;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    public UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new ClientUserDetailsServiceImpl());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients().checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(
                jwtAccessTokenConverter()
        );
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        /*var converter = new CustomAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray()
                );

        converter.setKeyPair(
                keyStoreKeyFactory.getKeyPair(alias));*/
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(privateKey),
                password.toCharArray()
        );

        converter.setKeyPair(
                keyStoreKeyFactory.getKeyPair(alias));

        return converter;
    }
}
