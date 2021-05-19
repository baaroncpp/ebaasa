package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserApprovalRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserMetaRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserServiceImp implements UserService {

    private String passwordMask = "**********";

    private TUserRepository tUserRepository;
    private TUserMetaRepository tUserMetaRepository;
    private TUserApprovalRepository tUserApprovalRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;

    @Autowired
    public UserServiceImp(TUserRepository tUserRepository,
                          TUserMetaRepository tUserMetaRepository,
                          TUserApprovalRepository tUserApprovalRepository,
                          UserManagementDTOMapperService userManagementDTOMapperService) {
        this.tUserRepository = tUserRepository;
        this.tUserMetaRepository = tUserMetaRepository;
        this.tUserApprovalRepository = tUserApprovalRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
    }

    @Override
    public Optional<TUser> addUser(UserDto userDto) {
        Validate.notNull(userDto, "NULL User object");
        Validate.notEmpty(userDto.getUsername(), "Username is not defined");
        Validate.notEmpty(userDto.getPassword(), "Password is not defined");

        Optional<TUser> tUser = tUserRepository.findByUsername(userDto.getUsername());
        if(tUser.isPresent()){
            throw new BadRequestException(String.format("Username %s is already taken", userDto.getUsername()));
        }
        TUser user = userManagementDTOMapperService.convertDTOToTUser(userDto);
        user.setCreatedOn(new Date());

        return Optional.of(tUserRepository.save(user));
    }

    @Override
    public Optional<TUser> getUserById(Long id) {
        Validate.notNull(id, "NULL ID value");
        var user = tUserRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with ID: %s not found", id))
        );
        user.setPassword(passwordMask);
        return Optional.of(user);
    }

    @Override
    public Optional<TUser> getUserByUsername(String username) {
        Validate.notEmpty(username, "Username not defined");
        var user = tUserRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("User with USERNAME: %s not found")
        );
        user.setPassword(passwordMask);
        return Optional.of(user);
    }

    @Override
    public List<TUser> getAllUsers(Pageable pageable) {
        return tUserRepository.findAllUsers(pageable);
    }
}
