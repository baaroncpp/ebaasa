package com.bkbwongo.core.ebaasa.usermgt.service.imp;

import com.bkbwongo.common.exceptions.BadRequestException;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.PermissionDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.RoleDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserGroupDto;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author bkaaron
 * @created on 14/05/2021
 * @project ebaasa-sms
 */
@Transactional
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
    public Optional<RoleDto> createUserRole(RoleDto role) {

        role.validate();

        Optional<TRole> tRole = roleRepository.findByName(role.getName());

        if(tRole.isPresent()){
            throw new BadRequestException(String.format("Role %s already exists", role.getName()));
        }

        var tr = userManagementDTOMapperService.convertDTOToTRole(role);
        auditService.stampLongEntity(tr);

        return Optional.of(userManagementDTOMapperService.convertTRoleToDTO(roleRepository.save(tr)));
    }

    @Override
    public Optional<RoleDto> updateUserRole(RoleDto role) {

        role.validate();

        roleRepository.findById(role.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User role with ID: %s does not exist", role.getId())));

        var result = userManagementDTOMapperService.convertDTOToTRole(role);
        auditService.stampLongEntity(result);

        return Optional.of(userManagementDTOMapperService.convertTRoleToDTO(roleRepository.save(result)));
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> userManagementDTOMapperService.convertTRoleToDTO(role))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionDto> addNewPermission(PermissionDto permission) {

        permission.validate();

        var tRole = roleRepository.findById(permission.getRole().getId())
                .orElseThrow(() -> new BadRequestException("User role not found"));

        Optional<TPermission> tPermission = permissionRepository.findByName(permission.getName());
        if(tPermission.isPresent()){
            throw new BadRequestException(String.format("Permission %s already exists", permission.getName()));
        }

        var tp = userManagementDTOMapperService.convertDTOToTPermission(permission);
        tp.setRole(tRole);
        System.out.println("test role"+tp.toString());
        auditService.stampLongEntity(tp);

        return Optional.of(userManagementDTOMapperService.convertTPermissionToDTO(permissionRepository.save(tp)));
    }

    @Override
    public Optional<PermissionDto> updatePermission(PermissionDto permission) {

        permission.validate();

        permissionRepository.findById(permission.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("User permission with ID: %s does not exist", permission.getId()))
                );

        var result = userManagementDTOMapperService.convertDTOToTPermission(permission);
        auditService.stampLongEntity(result);
        return Optional.of(userManagementDTOMapperService.convertTPermissionToDTO(permissionRepository.save(result)));
    }

    @Transactional
    @Override
    public List<PermissionDto> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permission -> userManagementDTOMapperService.convertTPermissionToDTO(permission))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserGroupDto> createUserGroup(UserGroupDto userGroup) {

        userGroup.validate();

        Optional<TUserGroup> tUserGroup = userGroupRepository.findByName(userGroup.getName());
        if(tUserGroup.isPresent()){
            throw new BadRequestException(String.format("UserGroup %s already exists", userGroup.getName()));
        }

        var tup = userManagementDTOMapperService.convertDTOToTUserGroup(userGroup);
        auditService.stampLongEntity(tup);

        return Optional.of(userManagementDTOMapperService.convertTUserGroupToDTO(userGroupRepository.save(tup)));
    }

    @Override
    public Optional<UserGroupDto> updateUserGroup(UserGroupDto userGroup) {

        userGroup.validate();

        userGroupRepository.findById(userGroup.getId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("UserGroup with ID: %s does not exist", userGroup.getId()))
                );

        var result = userManagementDTOMapperService.convertDTOToTUserGroup(userGroup);
        auditService.stampLongEntity(result);
        return Optional.of(userManagementDTOMapperService.convertTUserGroupToDTO(userGroupRepository.save(result)));
    }

    @Override
    public List<UserGroupDto> getAllUserGroups() {
        return userGroupRepository.findAll().stream()
                .map(userGroup -> userManagementDTOMapperService.convertTUserGroupToDTO(userGroup))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GroupAuthorityDto> addPermissionToUserGroup(GroupAuthorityDto groupAuthorityDto) {

        groupAuthorityDto.validate();

        var tUserGroup = userGroupRepository.findById(groupAuthorityDto.getUserGroup().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("UserGroup with ID %s does not exist", groupAuthorityDto.getUserGroup().getId()))
                );

        var tPermission = permissionRepository.findById(groupAuthorityDto.getPermission().getId())
                .orElseThrow(
                        () -> new BadRequestException(String.format("Permission with ID %s does not exist", groupAuthorityDto.getPermission().getId()))
                );

        Optional<TGroupAuthority> tGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(tUserGroup, tPermission);
        if(tGroupAuthority.isPresent()){
            throw new BadRequestException(String.format("UserGroup %s already has %s permission", tUserGroup.getName(), tPermission.getName()));
        }

        var tga = userManagementDTOMapperService.convertDTOToTGroupAuthority(groupAuthorityDto);
        auditService.stampLongEntity(tga);

        return Optional.of(userManagementDTOMapperService.convertTGroupAuthorityToDTO(tga));
    }

    @Override
    public Optional<GroupAuthorityDto> removePermissionToUserGroup(Long id) {

        Validate.notNull(id, "GroupAuthority ID not defined");

        var tGroupAuthority = groupAuthorityRepository.findById(id)
                .orElseThrow(
                        () -> new BadRequestException(String.format("GroupAuthority with ID %s does not exist", id))
                );
        groupAuthorityRepository.delete(tGroupAuthority);
        return Optional.of(userManagementDTOMapperService.convertTGroupAuthorityToDTO(tGroupAuthority));
    }

    @Override
    public List<GroupAuthorityDto> getGroupAuthorities(Long groupId) {

        Validate.notNull(groupId, "Group ID is not defined");

        TUserGroup userGroup = userGroupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BadRequestException(String.format("UserGroup with ID %s does not exist", groupId))
                );

        return groupAuthorityRepository.findByUserGroup(userGroup).stream()
                .map(groupAuthority -> userManagementDTOMapperService.convertTGroupAuthorityToDTO(groupAuthority))
                .collect(Collectors.toList());
    }
}
