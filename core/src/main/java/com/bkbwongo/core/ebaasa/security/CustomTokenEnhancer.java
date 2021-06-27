package com.bkbwongo.core.ebaasa.security;

import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bkaaron
 * @created on 26/06/2021
 * @project ebaasa-sms
 */
public class CustomTokenEnhancer extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        EbaasaLoginUser user = (EbaasaLoginUser) authentication.getPrincipal();

        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        return super.enhance(accessToken, authentication);
    }
}
