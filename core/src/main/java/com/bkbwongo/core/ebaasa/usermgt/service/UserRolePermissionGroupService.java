package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.PermissionDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.RoleDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserGroupDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TGroupAuthority;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TPermission;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TRole;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
public interface UserRolePermissionGroupService {
    Optional<RoleDto> createUserRole(RoleDto role);
    Optional<RoleDto> updateUserRole(RoleDto role);
    List<RoleDto> getAllRoles();

    Optional<PermissionDto> addNewPermission(PermissionDto permission);
    Optional<PermissionDto> updatePermission(PermissionDto permission);
    List<PermissionDto> getAllPermissions();

    Optional<UserGroupDto> createUserGroup(UserGroupDto userGroup);
    Optional<UserGroupDto> updateUserGroup(UserGroupDto userGroup);
    List<UserGroupDto> getAllUserGroups();

    Optional<GroupAuthorityDto> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto);
    Optional<GroupAuthorityDto> removePermissionToUserGroup(Long id);
    List<GroupAuthorityDto> getGroupAuthorities(Long groupId);

}
