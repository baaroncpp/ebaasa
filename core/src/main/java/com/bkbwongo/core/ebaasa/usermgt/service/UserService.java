package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserApprovalDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserPreviousPasswordDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 06/05/2021
 * @project ebaasa-sms
 */
public interface UserService {
    Optional<UserDto> addUser(UserDto userDto);
    Optional<UserDto> updateUser(UserDto userDto);
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByUsername(String username);
    Optional<UserDto> approveUser(UserApprovalDto userApprovalDto);
    Optional<UserPreviousPasswordDto> changePassword(String username, String oldPassword, String newPassword, Long userId, String note);
    List<UserDto> getAllUsers(Pageable pageable);
}
