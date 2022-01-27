package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.ApprovalEnum;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserApprovalDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserPreviousPasswordDto;
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
import java.util.stream.Collectors;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditService auditService;

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
    public Optional<UserDto> addUser(UserDto userDto) {

        userDto.validate();

        Optional<TUser> tUser = tUserRepository.findByUsername(userDto.getUsername());
        if(tUser.isPresent()){
            throw new BadRequestException(String.format(ErrorMessageConstants.USERNAME_IS_TAKEN, userDto.getUsername()));
        }
        var user = userManagementDTOMapperService.convertDTOToTUser(userDto);
        auditService.stampLongEntity(user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        var result = tUserRepository.save(user);
        result.setPassword(PASSWORD_MASK);

        return Optional.of(userManagementDTOMapperService.convertTUserToDTO(result));
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto) {

        userDto.validate();

        var user = tUserRepository.findById(userDto.getId()).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, userDto.getId()))
        );

        if(!userDto.getUsername().equalsIgnoreCase(user.getUsername())){
            Optional<TUser> tUser = tUserRepository.findByUsername(userDto.getUsername());
            if(tUser.isPresent()){
                throw new BadRequestException(String.format(ErrorMessageConstants.USERNAME_IS_TAKEN, userDto.getUsername()));
            }
        }

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new BadRequestException("Password modification not authorized");
        }

        var updatedUser = userManagementDTOMapperService.convertDTOToTUser(userDto);
        updatedUser.setPassword(user.getPassword());

        var result = tUserRepository.save(updatedUser);
        result.setPassword(PASSWORD_MASK);

        return Optional.of(userManagementDTOMapperService.convertTUserToDTO(result));
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Validate.notNull(id, "NULL ID value");
        var user = tUserRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, id))
        );
        user.setPassword(PASSWORD_MASK);
        return Optional.of(userManagementDTOMapperService.convertTUserToDTO(user));
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        Validate.notEmpty(username, "Username not defined");
        var user = tUserRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("User with USERNAME: %s not found")
        );
        user.setPassword(PASSWORD_MASK);
        return Optional.of(userManagementDTOMapperService.convertTUserToDTO(user));
    }

    @Override
    public Optional<UserDto> approveUser(UserApprovalDto userApprovalDto) {

        userApprovalDto.validate();

        var user = tUserRepository.findById(userApprovalDto.getUserId()).orElseThrow(
                () -> new BadRequestException(ErrorMessageConstants.ID_NOT_FOUND, userApprovalDto.getUserId())
        );

        var tUserApproval = userManagementDTOMapperService.convertDTOToTUserApproval(userApprovalDto);
        auditService.stampAuditedEntity(tUserApproval);

        tUserApprovalRepository.save(tUserApproval);

        var result = changeUserStatus(user, tUserApproval.getCreatedBy(), tUserApproval.getStatus()).get();
        result.setPassword(PASSWORD_MASK);

        return Optional.of(userManagementDTOMapperService.convertTUserToDTO(tUserRepository.save(result)));
    }

    @Override
    public Optional<UserPreviousPasswordDto> changePassword(String username,
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
                () -> new BadRequestException(String.format(ErrorMessageConstants.ID_NOT_FOUND, userId))
        );

        if(!passwordEncoder.matches(oldPassword, changedUser.getPassword())){
            throw new BadRequestException("Old password does not match");
        }

        if(passwordEncoder.matches(newPassword, changedUser.getPassword())){
            throw new BadRequestException("No password change");
        }

        changedUser.setPassword(passwordEncoder.encode(newPassword));

        var updatedUser = tUserRepository.save(changedUser);
        updatedUser.setPassword(PASSWORD_MASK);

        changingUser.setPassword(PASSWORD_MASK);

        var tUserPreviousPassword = new TUserPreviousPassword();
        tUserPreviousPassword.setPassword(changedUser.getPassword());
        tUserPreviousPassword.setUser(updatedUser);
        tUserPreviousPassword.setModifiedBy(changingUser);
        tUserPreviousPassword.setRemovalTime(new Date());
        tUserPreviousPassword.setNote(note);

        var result = tUserPreviousPasswordRepository.save(tUserPreviousPassword);

        return Optional.of(userManagementDTOMapperService.convertTUserPreviousPasswordToDTO(result));
    }

    @Override
    public List<UserDto> getAllUsers(Pageable pageable) {
        return tUserRepository.findAll(pageable).getContent().stream()
                .map(user -> userManagementDTOMapperService.convertTUserToDTO(user))
                .collect(Collectors.toList());
    }

    Optional<TUser> changeUserStatus(TUser user, TUser approvingUser, ApprovalEnum status){

        if(status.equals(ApprovalEnum.APPROVED)){
            user.setApproved(Boolean.TRUE);
            user.setAccountLocked(Boolean.TRUE);
            user.setAccountExpired(Boolean.TRUE);
            user.setDeleted(Boolean.FALSE);
            user.setApprovedBy(approvingUser.getId());
            user.setModifiedOn(DateTimeUtil.getCurrentUTCTime());
        }

        if(status.equals(ApprovalEnum.PENDING)){
            user.setApproved(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setModifiedOn(DateTimeUtil.getCurrentUTCTime());
        }

        if(status.equals(ApprovalEnum.REJECTED)){
            user.setApproved(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setModifiedOn(DateTimeUtil.getCurrentUTCTime());
        }
        return Optional.of(user);
    }
}
