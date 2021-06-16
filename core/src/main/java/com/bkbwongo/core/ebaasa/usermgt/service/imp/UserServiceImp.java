package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserApprovalDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserPreviousPassword;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserApprovalRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserPreviousPasswordRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private TUserRepository tUserRepository;
    private TUserApprovalRepository tUserApprovalRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;
    private TUserPreviousPasswordRepository tUserPreviousPasswordRepository;

    private static final String PASSWORD_MASK = "**********";
    private static final String USERNAME_NOT_FOUND = "Username is not defined";
    private static final  String ID_NOT_FOUND = "User with ID: %s not found";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(TUserRepository tUserRepository,
                          TUserApprovalRepository tUserApprovalRepository,
                          UserManagementDTOMapperService userManagementDTOMapperService,
                          TUserPreviousPasswordRepository tUserPreviousPasswordRepository) {
        this.tUserRepository = tUserRepository;
        this.tUserApprovalRepository = tUserApprovalRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
        this.tUserPreviousPasswordRepository = tUserPreviousPasswordRepository;
    }

    @Override
    public Optional<TUser> addUser(UserDto userDto) {
        Validate.notNull(userDto, "NULL User object");
        Validate.notEmpty(userDto.getUsername(), USERNAME_NOT_FOUND);
        Validate.notEmpty(userDto.getPassword(), "Password is not defined");

        Optional<TUser> tUser = tUserRepository.findByUsername(userDto.getUsername());
        if(tUser.isPresent()){
            throw new BadRequestException(String.format("Username %s is already taken", userDto.getUsername()));
        }
        var user = userManagementDTOMapperService.convertDTOToTUser(userDto);
        user.setCreatedOn(new Date());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        TUser result = tUserRepository.save(user);
        result.setPassword(PASSWORD_MASK);

        return Optional.of(result);
    }

    @Override
    public Optional<TUser> updateUser(UserDto userDto) {
        Validate.notNull(userDto, "NULL User object");
        Validate.notNull(userDto.getId(), "ID is not defined");
        Validate.notEmpty(userDto.getUsername(), USERNAME_NOT_FOUND);
        Validate.notEmpty(userDto.getPassword(), "Password is not defined");

        var user = tUserRepository.findById(userDto.getId()).orElseThrow(
                () -> new BadRequestException(String.format(ID_NOT_FOUND, userDto.getId()))
        );

        if(!userDto.getUsername().equalsIgnoreCase(user.getUsername())){
            Optional<TUser> tUser = tUserRepository.findByUsername(userDto.getUsername());
            if(tUser.isPresent()){
                throw new BadRequestException(String.format("Username %s is already taken", userDto.getUsername()));
            }
        }

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new BadRequestException("Password modification not authorized");
        }

        var updatedUser = userManagementDTOMapperService.convertDTOToTUser(userDto);
        updatedUser.setPassword(user.getPassword());

        TUser result = tUserRepository.save(updatedUser);

        return Optional.of(result);
    }

    @Override
    public Optional<TUser> getUserById(Long id) {
        Validate.notNull(id, "NULL ID value");
        var user = tUserRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ID_NOT_FOUND, id))
        );
        user.setPassword(PASSWORD_MASK);
        return Optional.of(user);
    }

    @Override
    public Optional<TUser> getUserByUsername(String username) {
        Validate.notEmpty(username, "Username not defined");
        var user = tUserRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("User with USERNAME: %s not found")
        );
        user.setPassword(PASSWORD_MASK);
        return Optional.of(user);
    }

    @Override
    public Optional<TUser> approveUser(UserApprovalDto userApprovalDto) {
        Validate.notNull(userApprovalDto.getUserId(), "NULL user ID");
        Validate.notNull(userApprovalDto.getCreatedBy(), "created by not defined");
        Validate.notNull(userApprovalDto.getStatus(), "Status not defined");

        var user = tUserRepository.findById(userApprovalDto.getUserId()).orElseThrow(
                () -> new BadRequestException(ID_NOT_FOUND, userApprovalDto.getUserId())
        );

        var approvingUser = tUserRepository.findById(userApprovalDto.getCreatedBy().getId()).orElseThrow(
                () -> new BadRequestException(ID_NOT_FOUND, userApprovalDto.getCreatedBy().getId())
        );

        var tUserApproval = userManagementDTOMapperService.convertDTOToTUserApproval(userApprovalDto);
        tUserApproval.setCreatedOn(new Date());

        tUserApprovalRepository.save(tUserApproval);

        return Optional.of(tUserRepository.save(
                changeUserStatus(user, approvingUser, tUserApproval.getStatus()).get())
        );
    }

    @Override
    public Optional<TUserPreviousPassword> changePassword(String username,
                                          String oldPassword,
                                          String newPassword,
                                          Long userId,
                                          String note) {
        Validate.notEmpty(username, USERNAME_NOT_FOUND);
        Validate.notEmpty(oldPassword, "Old Password is not defined");
        Validate.notEmpty(newPassword, "new Password is not defined");

        var changingUser = tUserRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("User with USERNAME: %s not found")
        );

        var changedUser = tUserRepository.findById(userId).orElseThrow(
                () -> new BadRequestException(String.format(ID_NOT_FOUND, userId))
        );

        if(!passwordEncoder.matches(oldPassword, changedUser.getPassword())){
            throw new BadRequestException("Old password does not match");
        }

        if(passwordEncoder.matches(newPassword, changedUser.getPassword())){
            throw new BadRequestException("No password change");
        }

        changedUser.setPassword(passwordEncoder.encode(newPassword));

        TUser updatedUser = tUserRepository.save(changedUser);
        updatedUser.setPassword(PASSWORD_MASK);

        changingUser.setPassword(PASSWORD_MASK);

        var tUserPreviousPassword = new TUserPreviousPassword();
        tUserPreviousPassword.setPassword(changedUser.getPassword());
        tUserPreviousPassword.setUser(updatedUser);
        tUserPreviousPassword.setModifiedBy(changingUser);
        tUserPreviousPassword.setRemovalTime(new Date());
        tUserPreviousPassword.setNote(note);

        var result = tUserPreviousPasswordRepository.save(tUserPreviousPassword);

        return Optional.of(result);
    }

    @Override
    public List<TUser> getAllUsers(Pageable pageable) {
        return tUserRepository.findAll(pageable).getContent();
    }

    Optional<TUser> changeUserStatus(TUser user, TUser approvingUser, ApprovalEnum status){

        if(status.equals(ApprovalEnum.APPROVED)){
            user.setApproved(Boolean.TRUE);
            user.setAccountLocked(Boolean.TRUE);
            user.setAccountExpired(Boolean.TRUE);
            user.setDeleted(Boolean.FALSE);
            user.setApprovedBy(approvingUser.getId());
            user.setModifiedOn(new Date());
        }

        if(status.equals(ApprovalEnum.PENDING)){
            user.setApproved(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setModifiedOn(new Date());
        }

        if(status.equals(ApprovalEnum.REJECTED)){
            user.setApproved(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setModifiedOn(new Date());
        }
        return Optional.of(user);
    }
}
