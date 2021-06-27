package com.bkbwongo.core.ebaasa.security.service;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.SetUtils;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.security.jpa.model.TAppClient;
import com.bkbwongo.core.ebaasa.security.model.ClientApp;
import com.bkbwongo.core.ebaasa.security.repository.TAppClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 04/05/2021
 * @project ebaasa-sms
 */
@Service
public class ClientUserDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private TAppClientRepository appClientRepository;


    @Override
    public ClientDetails loadClientByClientId(String clientId) {

        Optional<TAppClient> appClient = appClientRepository.getByName(clientId);

        Validate.isTrue(appClient.isPresent(), ErrorMessageConstants.APP_CLIENT_NOT_FOUND,clientId);

        TAppClient client = null;
        if(appClient.isPresent()){
            client = appClient.get();
        }

        Validate.isTrue(client !=null && client.getEnabled(),ErrorMessageConstants.APP_CLIENT_NOT_ENABLED,clientId);

        return getClientDetails(client);
    }

    private ClientDetails getClientDetails(TAppClient appClient){

        ClientApp details = new ClientApp();
        BeanUtils.copyProperties(appClient,details);
        details.setAuthorizedGrantTypes(SetUtils.getSetFromStringWithSeparator(appClient.getGrantTypes()));
        details.setRegisteredRedirectUri(null);
        details.setScopes(SetUtils.getSetFromStringWithSeparator(appClient.getScope()));
        details.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(appClient.getAuthorities()));
        return details;

    }
}
