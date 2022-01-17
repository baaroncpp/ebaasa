package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
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
    public ResponseEntity<Object> addUserRole(@RequestBody RoleDto role){
        return ResponseEntity.ok(userRolePermissionGroupService.createUserRole(role));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/role", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateUserRole(@RequestBody RoleDto role){
        return ResponseEntity.ok(userRolePermissionGroupService.updateUserRole(role));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/roles", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllUserRoles(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllRoles());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/permission", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addPermission(@RequestBody PermissionDto permission){
        return ResponseEntity.ok(userRolePermissionGroupService.addNewPermission(permission));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/permission", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updatePermission(@RequestBody PermissionDto permission){
        return ResponseEntity.ok(userRolePermissionGroupService.updatePermission(permission));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/permissions", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllPermissions(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllPermissions());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addUserGroup(@RequestBody UserGroupDto userGroup){
        return ResponseEntity.ok(userRolePermissionGroupService.createUserGroup(userGroup));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateUserGroup(@RequestBody UserGroupDto userGroup){
        return ResponseEntity.ok(userRolePermissionGroupService.updateUserGroup(userGroup));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/groups", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllUserGroups(){
        return ResponseEntity.ok(userRolePermissionGroupService.getAllUserGroups());
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/group/authority", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addPermissionToUserGroup(@RequestBody GroupAuthorityDto groupAuthorityDto){
        return ResponseEntity.ok(userRolePermissionGroupService.addPermissionToUserGroup(groupAuthorityDto));
    }

    @RolesAllowed("ROLE_ADMIN.DELETE")
    @DeleteMapping(value = "/group/authority/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> removeGroupAuthority(@PathVariable("id") Long id){
        return ResponseEntity.ok(userRolePermissionGroupService.removePermissionToUserGroup(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/group/authorities/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getGroupAuthorities(@PathVariable("id") Long id){
        return ResponseEntity.ok(userRolePermissionGroupService.getGroupAuthorities(id));
    }
}
