package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.api.BaseAPI;
import com.bkbwongo.core.ebaasa.usermgt.dto.GroupAuthorityDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.PermissionDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.RoleDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserGroupDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.service.UserManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.usermgt.service.UserRolePermissionGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 15/05/2021
 * @project ebaasa-sms
 */
@Tag(name = "Permissions", description = "Manage all user roles, user groups and group permissions")
@RestController
@RequestMapping("/api")
public class UserRolePermissionGroupApi {

    @Autowired
    private UserRolePermissionGroupService userRolePermissionGroupService;

    @Autowired
    private UserManagementDTOMapperService userManagementDTOMapperService;

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/role", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> addUserRole(@RequestBody RoleDto role){
        return ResponseEntity.ok(userRolePermissionGroupService.createUserRole(role));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/role", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> updateUserRole(@RequestBody RoleDto role){
        return ResponseEntity.ok(userRolePermissionGroupService.updateUserRole(role));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/role", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> getAllUserRoles(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllRoles());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/permission", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> addPermission(@RequestBody PermissionDto permission){
        return ResponseEntity.ok(userRolePermissionGroupService.addNewPermission(permission));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/permission", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> updatePermission(@RequestBody PermissionDto permission){
        return ResponseEntity.ok(userRolePermissionGroupService.updatePermission(permission));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/permission", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> getAllPermissions(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllPermissions());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> addUserGroup(@RequestBody UserGroupDto userGroup){
        return ResponseEntity.ok(userRolePermissionGroupService.createUserGroup(userGroup));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> updateUserGroup(@RequestBody UserGroupDto userGroup){
        return ResponseEntity.ok(userRolePermissionGroupService.updateUserGroup(userGroup));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/group", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> getAllUserGroups(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllUserGroups());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/group/authority", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> addPermissionToUserGroup(@RequestBody GroupAuthorityDto groupAuthorityDto){
        return ResponseEntity.ok(userRolePermissionGroupService.addPermissionToUserGroup(groupAuthorityDto));
    }

    @RolesAllowed("ROLE_ADMIN.DELETE")
    @DeleteMapping(value = "/group/authority/remove/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> removeGroupAuthority(@PathVariable("id") Long id){
        return ResponseEntity.ok(userRolePermissionGroupService.removePermissionToUserGroup(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @PostMapping(value = "/group/authorities/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<?> getGroupAuthorities(@PathVariable("id") Long id){
        return ResponseEntity.ok(userRolePermissionGroupService.getGroupAuthorities(id));
    }
}
