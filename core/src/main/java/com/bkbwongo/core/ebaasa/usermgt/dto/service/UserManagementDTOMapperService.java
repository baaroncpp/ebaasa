package com.bkbwongo.core.ebaasa.usermgt.dto.service;

import com.bkbwongo.core.ebaasa.usermgt.dto.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.PermissionDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.RoleDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserGroupDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TGroupAuthority;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TPermission;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TRole;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserGroup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bkaaron
 * @created on 16/05/2021
 * @project ebaasa-sms
 */
@Service
public class UserManagementDTOMapperService {

    @Autowired
    ModelMapper modelMapper;

    public TRole convertDTOToTRole(RoleDto roleDto){
        return modelMapper.map(roleDto, TRole.class);
    }

    public RoleDto convertTRoleToDTO(TRole tRole){
        return modelMapper.map(tRole, RoleDto.class);
    }

    public UserGroupDto convertTUserGroupToDTO(TUserGroup tUserGroup){
        return modelMapper.map(tUserGroup, UserGroupDto.class);
    }

    public TUserGroup convertDTOToTUserGroup(UserGroupDto userGroupDto){
        return modelMapper.map(userGroupDto, TUserGroup.class);
    }

    public TPermission convertDTOToTPermission(PermissionDto permissionDto){
        var tPermission = modelMapper.map(permissionDto, TPermission.class);
        tPermission.setRole(convertDTOToTRole(permissionDto.getRoleDto()));
        return tPermission;
    }

    public PermissionDto convertTPermissionToDTO(TPermission tPermission){
        var permissionDto = modelMapper.map(tPermission, PermissionDto.class);
        permissionDto.setRoleDto(convertTRoleToDTO(tPermission.getRole()));
        return permissionDto;
    }

    public TGroupAuthority convertDTOToTGroupAuthority(GroupAuthorityDto groupAuthorityDto){
        var tGroupAuthority = modelMapper.map(groupAuthorityDto, TGroupAuthority.class);
        tGroupAuthority.setUserGroup(convertDTOToTUserGroup(groupAuthorityDto.getUserGroupDto()));
        tGroupAuthority.setPermission(convertDTOToTPermission(groupAuthorityDto.getPermissionDto()));
        return tGroupAuthority;
    }

    public GroupAuthorityDto convertTGroupAuthorityToDTO(TGroupAuthority tGroupAuthority){
        var groupAuthorityDto = modelMapper.map(tGroupAuthority, GroupAuthorityDto.class);
        groupAuthorityDto.setUserGroupDto(convertTUserGroupToDTO(tGroupAuthority.getUserGroup()));
        groupAuthorityDto.setPermissionDto(convertTPermissionToDTO(tGroupAuthority.getPermission()));
        return groupAuthorityDto;
    }

}
