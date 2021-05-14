package com.bkbwongo.core.ebaasa.usermgt.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TGroupAuthority;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TPermission;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TRole;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
public interface UserRolePermissionGroupService {
    Optional<TRole> createUserRole(TRole role);
    Optional<TRole> updateUserRole(TRole role);

    Optional<TPermission> addNewPermission(TPermission permission);
    Optional<TPermission> updatePermission(TPermission permission);

    Optional<TUserGroup> createUserGroup(TUserGroup userGroup);
    Optional<TUserGroup> updateUserGroup(TUserGroup userGroup);

    Optional<TGroupAuthority> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto);
    Optional<TGroupAuthority> removePermissionToUserGroup(Long id);
}
