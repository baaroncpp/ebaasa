package com.bkbwongo.core.ebaasa.security;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterMinutes;

    public JwtConfig() {}

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterMinutes() {
        return tokenExpirationAfterMinutes;
    }

    public void setTokenExpirationAfterMinutes(Integer tokenExpirationAfterMinutes) {
        this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
    }

    @Bean
    public SecretKey getSecretKeyForSigning(){
        return Keys.hmacShaKeyFor(getSecretKey().getBytes());
    }

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
