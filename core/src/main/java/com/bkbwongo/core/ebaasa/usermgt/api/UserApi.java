package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserApprovalDto;
import com.bkbwongo.core.ebaasa.usermgt.dto.UserDto;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
@Tag(name = "Users", description = "Manage all user")
@RestController
@RequestMapping("api")
public class UserApi {

    @Autowired
    private UserService userService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/user", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/user", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> approveUser(@RequestBody UserApprovalDto userApprovalDto){
        return ResponseEntity.ok(userService.approveUser(userApprovalDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/user", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/user", params = {"userId", "newPassword", "oldPassword", "note"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> changePassword(@RequestParam("newPassword") String newPassword,
                                                 @RequestParam("oldPassword") String oldPassword,
                                                 @RequestParam("userId") Long userId,
                                                 @RequestParam("note") String note){
        var username = "";
        return ResponseEntity.ok(userService.changePassword(username, oldPassword, newPassword, userId, note));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/user/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/user/{username}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/users", params = { "page", "size" ,"sort"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllUsers(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sort") String sort){
        Pageable pageable = null;

        switch(sort) {
            case SORT_DESC :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY).descending());
                break;
            case SORT_ASC :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY).ascending());
                break;
            default :
                pageable = PageRequest.of(page, size, Sort.by(SORT_BY));
        }
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

}
