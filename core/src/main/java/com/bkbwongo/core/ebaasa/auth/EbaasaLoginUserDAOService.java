package com.bkbwongo.core.ebaasa.auth;

import com.bkbwongo.common.exceptions.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.enums.UserTypeEnum;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TGroupAuthority;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;
import com.bkbwongo.core.ebaasa.usermgt.repository.TGroupAuthorityRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
@Service
public class EbaasaLoginUserDAOService implements EbaasaLoginUserDAO{

    private TUserRepository TUserRepository;
    private TGroupAuthorityRepository groupAuthorityRepository;

    @Autowired
    public EbaasaLoginUserDAOService(TUserRepository TUserRepository,
                                     TGroupAuthorityRepository groupAuthorityRepository) {
        this.TUserRepository = TUserRepository;
        this.groupAuthorityRepository = groupAuthorityRepository;
    }

    @Override
    public Optional<EbaasaLoginUser> selectLoginUserByUsername(String username) {

        Optional<TUser> user = TUserRepository.findByUsername(username);
        Validate.isTrue(user.isPresent(), ErrorMessageConstants.USER_NOT_FOUND);

        return mapUserDetails(user.get());
    }

    private Optional<EbaasaLoginUser> mapUserDetails(TUser user){

        TUserGroup userGroup = user.getUserGroup();
        Validate.notNull(userGroup, "User group is NULL");

        List<TGroupAuthority> groupAuthorities = groupAuthorityRepository.findByUserGroup(userGroup);
        Validate.notNull(groupAuthorities, String.format("user %s has no permissions, service access denied", user.getUsername()));

        Set<SimpleGrantedAuthority> permissions = groupAuthorities
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getName()))
                .collect(Collectors.toSet());

        var logInUser = new EbaasaLoginUser();

        logInUser.setUsername(user.getUsername());
        logInUser.setPassword(user.getPassword());
        logInUser.setAccountNonExpired(user.isAccountExpired());
        logInUser.setAccountNonLocked(user.isAccountLocked());
        logInUser.setEnabled(user.isApproved());
        logInUser.setCredentialsNonExpired(user.isCredentialExpired());
        logInUser.setId(user.getId());
        logInUser.setGrantedAuthorities(permissions);

        if(user.getUserType().equals(UserTypeEnum.COMPANY)){
            logInUser.setCompanyUser(Boolean.TRUE);
        }else{
            logInUser.setCompanyUser(Boolean.FALSE);
        }

        return Optional.of(logInUser);
    }
}
