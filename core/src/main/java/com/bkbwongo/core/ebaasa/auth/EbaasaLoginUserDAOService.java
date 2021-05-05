package com.bkbwongo.core.ebaasa.auth;

import com.bkbwongo.common.exceptions.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.enums.UserTypeEnum;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
@Repository("postgres-user")
public class EbaasaLoginUserDAOService implements EbaasaLoginUserDAO{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<EbaasaLoginUser> selectLoginUserByUsername(String username) {

        Optional<TUser> user = userRepository.findByUsername(username);
        Validate.isTrue(user.isPresent(), ErrorMessageConstants.USER_NOT_FOUND);

        return mapUserDetails(user.get());
    }

    private Optional<EbaasaLoginUser> mapUserDetails(TUser user){
        var logInUser = new EbaasaLoginUser();

        logInUser.setUsername(user.getUsername());
        logInUser.setPassword(user.getPassword());
        logInUser.setAccountNonExpired(user.isAccountExpired());
        logInUser.setAccountNonLocked(user.isAccountLocked());
        logInUser.setEnabled(user.isApproved());
        logInUser.setCredentialsNonExpired(user.isCredentialExpired());
        logInUser.setId(user.getId());
        logInUser.setGrantedAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserAuthority().getAuthority()));// TODO when roles and permissions are specified

        if(user.getUserType().equals(UserTypeEnum.COMPANY)){
            logInUser.setCompanyUser(Boolean.TRUE);
        }else{
            logInUser.setCompanyUser(Boolean.FALSE);
        }

        return Optional.of(logInUser);
    }
}
