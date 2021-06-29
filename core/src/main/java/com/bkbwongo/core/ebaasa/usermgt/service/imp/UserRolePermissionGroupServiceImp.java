package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
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

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserRolePermissionGroupServiceImp implements UserRolePermissionGroupService {

    @Autowired
    private AuditService auditService;

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

        role.validate();

        Optional<TRole> tRole = roleRepository.findByName(role.getName());

        if(tRole.isPresent()){
            throw new BadRequestException(String.format("Role %s already exists", role.getName()));
        }

        var tr = userManagementDTOMapperService.convertDTOToTRole(role);
        auditService.stampLongEntity(tr);

        return Optional.of(roleRepository.save(tr));
    }

    @Override
    public Optional<TRole> updateUserRole(RoleDto role) {

        role.validate();

        roleRepository.findById(role.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User role with ID: %s does not exist", role.getId())));

        var result = userManagementDTOMapperService.convertDTOToTRole(role);
        auditService.stampLongEntity(result);

        return Optional.of(roleRepository.save(result));
    }

    @Override
    public List<TRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<TPermission> addNewPermission(PermissionDto permission) {

        permission.validate();

        var tRole = roleRepository.findById(permission.getId())
                .orElseThrow(() -> new BadRequestException("User role not found"));

        Optional<TPermission> tPermission = permissionRepository.findByName(permission.getName());
        if(tPermission.isPresent()){
            throw new BadRequestException(String.format("Permission %s already exists", permission.getName()));
        }

        var tp = userManagementDTOMapperService.convertDTOToTPermission(permission);
        auditService.stampLongEntity(tp);

        return Optional.of(permissionRepository.save(tp));
    }

    @Override
    public Optional<TPermission> updatePermission(PermissionDto permission) {

        permission.validate();

        permissionRepository.findById(permission.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User permission with ID: %s does not exist", permission.getId()))
                );

        var result = userManagementDTOMapperService.convertDTOToTPermission(permission);
        auditService.stampLongEntity(result);
        return Optional.of(permissionRepository.save(result));
    }

    @Override
    public List<TPermission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<TUserGroup> createUserGroup(UserGroupDto userGroup) {

        userGroup.validate();

        Optional<TUserGroup> tUserGroup = userGroupRepository.findByName(userGroup.getName());
        if(tUserGroup.isPresent()){
            throw new BadRequestException(String.format("UserGroup %s already exists", userGroup.getName()));
        }

        var tup = userManagementDTOMapperService.convertDTOToTUserGroup(userGroup);
        auditService.stampLongEntity(tup);

        return Optional.of(userGroupRepository.save(tup));
    }

    @Override
    public Optional<TUserGroup> updateUserGroup(UserGroupDto userGroup) {

        userGroup.validate();

        userGroupRepository.findById(userGroup.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("UserGroup with ID: %s does not exist", userGroup.getId()))
                );

        var result = userManagementDTOMapperService.convertDTOToTUserGroup(userGroup);
        auditService.stampLongEntity(result);
        return Optional.of(userGroupRepository.save(result));
    }

    @Override
    public List<TUserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }

    @Override
    public Optional<TGroupAuthority> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto) {

        groupAuthorityDto.validate();

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
        auditService.stampLongEntity(tga);
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
