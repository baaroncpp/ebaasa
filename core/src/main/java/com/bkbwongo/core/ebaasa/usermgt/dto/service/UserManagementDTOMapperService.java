package com.bkbwongo.core.ebaasa.usermgt.dto.service;

import com.bkbwongo.core.ebaasa.base.dto.service.CoreDTOService;
import com.bkbwongo.core.ebaasa.usermgt.dto.*;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.*;
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
    private ModelMapper modelMapper;

    @Autowired
    private CoreDTOService coreDTOService;

    private final static String PASSWORD_MASK = "******";

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
        tPermission.setRole(convertDTOToTRole(permissionDto.getRole()));
        return tPermission;
    }

    public PermissionDto convertTPermissionToDTO(TPermission tPermission){
        var permissionDto = modelMapper.map(tPermission, PermissionDto.class);
        permissionDto.setRole(convertTRoleToDTO(tPermission.getRole()));
        return permissionDto;
    }

    public TGroupAuthority convertDTOToTGroupAuthority(GroupAuthorityDto groupAuthorityDto){
        var tGroupAuthority = modelMapper.map(groupAuthorityDto, TGroupAuthority.class);
        tGroupAuthority.setUserGroup(convertDTOToTUserGroup(groupAuthorityDto.getUserGroup()));
        tGroupAuthority.setPermission(convertDTOToTPermission(groupAuthorityDto.getPermission()));
        return tGroupAuthority;
    }

    public GroupAuthorityDto convertTGroupAuthorityToDTO(TGroupAuthority tGroupAuthority){
        var groupAuthorityDto = modelMapper.map(tGroupAuthority, GroupAuthorityDto.class);
        groupAuthorityDto.setUserGroup(convertTUserGroupToDTO(tGroupAuthority.getUserGroup()));
        groupAuthorityDto.setPermission(convertTPermissionToDTO(tGroupAuthority.getPermission()));
        return groupAuthorityDto;
    }

    public TUser convertDTOToTUser(UserDto userDto){
        var tUser = modelMapper.map(userDto, TUser.class);
        tUser.setUserGroup(convertDTOToTUserGroup(userDto.getUserGroup()));
        return tUser;
    }

    public UserDto convertTUserToDTO(TUser tUser){
        var userDto = modelMapper.map(tUser, UserDto.class);
        userDto.setUserGroup(convertTUserGroupToDTO(tUser.getUserGroup()));
        userDto.setPassword(PASSWORD_MASK);
        return userDto;
    }

    public TUserMeta convertDTOToTUserMeta(UserMetaDto userMetaDto){
        var tUserMeta = modelMapper.map(userMetaDto, TUserMeta.class);
        tUserMeta.setCreatedBy(convertDTOToTUser(userMetaDto.getCreatedBy()));

        if(userMetaDto.getModifiedBy() != null){
            tUserMeta.setModifiedBy(convertDTOToTUser(userMetaDto.getModifiedBy()));
        }
        return tUserMeta;
    }

    public UserMetaDto convertTUserMetaToDTO(TUserMeta tUserMeta){
        var userMetaDto = modelMapper.map(tUserMeta, UserMetaDto.class);
        userMetaDto.setCreatedBy(convertTUserToDTO(tUserMeta.getCreatedBy()));

        if(tUserMeta.getModifiedBy() != null){
            userMetaDto.setCreatedBy(convertTUserToDTO(tUserMeta.getCreatedBy()));
        }
        return userMetaDto;
    }

    public TUserApproval convertDTOToTUserApproval(UserApprovalDto userApprovalDto){
        var tUserApproval = modelMapper.map(userApprovalDto, TUserApproval.class);
        tUserApproval.setCreatedBy(convertDTOToTUser(userApprovalDto.getCreatedBy()));

        if(userApprovalDto.getModifiedBy() != null){
            tUserApproval.setModifiedBy(convertDTOToTUser(userApprovalDto.getModifiedBy()));
        }
        return tUserApproval;
    }

    public UserApprovalDto convertTUserApprovalToDTO(TUserApproval tUserApproval){
        var userApprovalDto = modelMapper.map(tUserApproval, UserApprovalDto.class);
        userApprovalDto.setCreatedBy(convertTUserToDTO(tUserApproval.getCreatedBy()));

        if(tUserApproval.getModifiedBy() != null){
            userApprovalDto.setModifiedBy(convertTUserToDTO(tUserApproval.getModifiedBy()));
        }
        return userApprovalDto;
    }

    public TCompany convertDTOToTCompany(CompanyDto companyDto){
        var tCompany = modelMapper.map(companyDto, TCompany.class);
        tCompany.setCreatedBy(convertDTOToTUser(companyDto.getCreatedBy()));
        tCompany.setDistrict(coreDTOService.convertDTOToTDistrict(companyDto.getDistrict()));
        tCompany.setRegistrationCountry(coreDTOService.convertDTOToTCountry(companyDto.getRegistrationCountry()));

        if (companyDto.getModifiedBy() != null){
            tCompany.setModifiedBy(convertDTOToTUser(companyDto.getModifiedBy()));
        }
        return tCompany;
    }

    public CompanyDto convertTCompanyToDTO(TCompany tCompany){
        var companyDto = modelMapper.map(tCompany, CompanyDto.class);
        companyDto.setCreatedBy(convertTUserToDTO(tCompany.getCreatedBy()));
        companyDto.setDistrict(coreDTOService.convertTDistrictToDTO(tCompany.getDistrict()));
        companyDto.setRegistrationCountry(coreDTOService.convertTCountryToDTO(tCompany.getRegistrationCountry()));

        if (tCompany.getModifiedBy() != null){
            companyDto.setModifiedBy(convertTUserToDTO(tCompany.getModifiedBy()));
        }
        return companyDto;
    }

}
