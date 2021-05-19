package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.usermgt.dto.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.PermissionDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.RoleDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserGroupDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TGroupAuthority;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TPermission;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TRole;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;
import com.bkbwongo.core.ebaasa.usermgt.repository.TGroupAuthorityRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TPermissionRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TRoleRepository;
import com.bkbwongo.core.ebaasa.usermgt.repository.TUserGroupRepository;
import com.bkbwongo.core.ebaasa.usermgt.service.UserRolePermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserRolePermissionGroupServiceImp implements UserRolePermissionGroupService {

    private TRoleRepository roleRepository;
    private TUserGroupRepository userGroupRepository;
    private TPermissionRepository permissionRepository;
    private TGroupAuthorityRepository groupAuthorityRepository;
    private UserManagementDTOMapperService userManagementDTOMapperService;

    @Autowired
    public UserRolePermissionGroupServiceImp(TRoleRepository roleRepository,
                                             TUserGroupRepository userGroupRepository,
                                             TPermissionRepository permissionRepository,
                                             TGroupAuthorityRepository groupAuthorityRepository,
                                             UserManagementDTOMapperService userManagementDTOMapperService) {
        this.roleRepository = roleRepository;
        this.userGroupRepository = userGroupRepository;
        this.permissionRepository = permissionRepository;
        this.groupAuthorityRepository = groupAuthorityRepository;
        this.userManagementDTOMapperService = userManagementDTOMapperService;
    }

    @Override
    public Optional<TRole> createUserRole(RoleDto role) {

        Validate.notNull(role, "NULL role object");
        Validate.notEmpty(role.getName(), "Role name is not defined");
        Validate.notEmpty(role.getNote(), "Role description is not defined");

        Optional<TRole> tRole = roleRepository.findByName(role.getName());

        if(tRole.isPresent()){
            throw new BadRequestException(String.format("Role %s already exists", role.getName()));
        }

        TRole tr = userManagementDTOMapperService.convertDTOToTRole(role);
        tr.setCreatedOn(new Date());

        return Optional.of(roleRepository.save(tr));
    }

    @Override
    public Optional<TRole> updateUserRole(RoleDto role) {

        Validate.notNull(role, "NULL role object");
        Validate.notEmpty(role.getName(), "Role name is not defined");

        roleRepository.findById(role.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User role with ID: %s does not exist", role.getId())));

        return Optional.of(roleRepository.save(
                userManagementDTOMapperService.convertDTOToTRole(role)));
    }

    @Override
    public List<TRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<TPermission> addNewPermission(PermissionDto permission) {

        Validate.notNull(permission, "NULL permission object");
        Validate.notNull(permission.getId(), "User role ID is NULL");
        Validate.notEmpty(permission.getName(), "permission name is not defined");

        TRole tRole = roleRepository.findById(permission.getId())
                .orElseThrow(() -> new BadRequestException("User role not found"));

        Optional<TPermission> tPermission = permissionRepository.findByName(permission.getName());
        if(tPermission.isPresent()){
            throw new BadRequestException(String.format("Permission %s already exists", permission.getName()));
        }

        TPermission tp = userManagementDTOMapperService.convertDTOToTPermission(permission);
        tp.setCreatedOn(new Date());

        return Optional.of(permissionRepository.save(tp));
    }

    @Override
    public Optional<TPermission> updatePermission(PermissionDto permission) {

        Validate.notNull(permission, "NULL permission object");
        Validate.notEmpty(permission.getName(), "Permission name is not defined");

        permissionRepository.findById(permission.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User permission with ID: %s does not exist", permission.getId()))
                );
        return Optional.of(permissionRepository.save(userManagementDTOMapperService.convertDTOToTPermission(permission)));
    }

    @Override
    public List<TPermission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<TUserGroup> createUserGroup(UserGroupDto userGroup) {

        Validate.notNull(userGroup, "NULL user group object");
        Validate.notEmpty(userGroup.getName(), "User group name is not defined");
        Validate.notEmpty(userGroup.getNote(), "User group description is not defined");

        Optional<TUserGroup> tUserGroup = userGroupRepository.findByName(userGroup.getName());
        if(tUserGroup.isPresent()){
            throw new BadRequestException(String.format("UserGroup %s already exists", userGroup.getName()));
        }

        var tup = new TUserGroup();
        tup.setName(userGroup.getName());
        tup.setNote(userGroup.getNote());
        tup.setCreatedOn(new Date());

        return Optional.of(userGroupRepository.save(tup));
    }

    @Override
    public Optional<TUserGroup> updateUserGroup(UserGroupDto userGroup) {

        Validate.notNull(userGroup, "NULL userGroup object");
        Validate.notEmpty(userGroup.getName(), "UserGroup name is not defined");

        userGroupRepository.findById(userGroup.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("UserGroup with ID: %s does not exist", userGroup.getId()))
                );
        return Optional.of(userGroupRepository.save(
                userManagementDTOMapperService.convertDTOToTUserGroup(userGroup)));
    }

    @Override
    public List<TUserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }

    @Override
    public Optional<TGroupAuthority> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto) {

        Validate.notNull(groupAuthorityDto, "NULL GroupAuthority object");
        Validate.notNull(groupAuthorityDto.getUserGroupDto().getId(), "UserGroup ID not defined");
        Validate.notNull(groupAuthorityDto.getPermissionDto().getId(), "Permission ID not defined");

        var tUserGroup = userGroupRepository.findById(groupAuthorityDto.getUserGroupDto().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("UserGroup with ID %s does not exist", groupAuthorityDto.getUserGroupDto().getId()))
                );

        var tPermission = permissionRepository.findById(groupAuthorityDto.getPermissionDto().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Permission with ID %s does not exist", groupAuthorityDto.getPermissionDto().getId()))
                );

        Optional<TGroupAuthority> tGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(tUserGroup, tPermission);
        if(tGroupAuthority.isPresent()){
            throw new BadRequestException(String.format("UserGroup %s already has %s permission", tUserGroup.getName(), tPermission.getName()));
        }

        var tga = userManagementDTOMapperService.convertDTOToTGroupAuthority(groupAuthorityDto);

        return Optional.of(tga);
    }

    @Override
    public Optional<TGroupAuthority> removePermissionToUserGroup(Long id) {

        Validate.notNull(id, "GroupAuthority ID not defined");

        var tGroupAuthority = groupAuthorityRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format("GroupAuthority with ID %s does not exist", id))
                );
        groupAuthorityRepository.delete(tGroupAuthority);
        return Optional.of(tGroupAuthority);
    }

    @Override
    public List<TGroupAuthority> getGroupAuthorities(Long groupId) {

        Validate.notNull(groupId, "Group ID is not defined");

        TUserGroup userGroup = userGroupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BadRequestException(String.format("UserGroup with ID %s does not exist", groupId))
                );
        return groupAuthorityRepository.findByUserGroup(userGroup);
    }
}
