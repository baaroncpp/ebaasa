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
    Optional<TRole> createUserRole(RoleDto role);
    Optional<TRole> updateUserRole(TRole role);
    List<TRole> getAllRoles();

    Optional<TPermission> addNewPermission(PermissionDto permission);
    Optional<TPermission> updatePermission(TPermission permission);
    List<TPermission> getAllPermissions();

    Optional<TUserGroup> createUserGroup(UserGroupDto userGroup);
    Optional<TUserGroup> updateUserGroup(TUserGroup userGroup);
    List<TUserGroup> getAllUserGroups();

    Optional<TGroupAuthority> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto);
    Optional<TGroupAuthority> removePermissionToUserGroup(Long id);
    List<TGroupAuthority> getGroupAuthorities(Long groupId);

}
