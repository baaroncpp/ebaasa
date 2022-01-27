package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.usermgt.dto.models.UserMetaDto;
import com.bkbwongo.core.ebaasa.usermgt.service.UserMetaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Tag(name = "User Meta Data", description = "Api for CRUD on User Meta Data")
@RestController
@RequestMapping("/api/user")
public class UserMetaApi {

    @Autowired
    private UserMetaService userMetaService;

    @RolesAllowed("ROLE_ADMIN.WRITE, ROLE_USER.WRITE")
    @PostMapping(value = "/meta", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addUserMeta(@RequestBody UserMetaDto userMetaDto){
        return ResponseEntity.ok(userMetaService.addUserMeta(userMetaDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE, ROLE_USER.UPDATE")
    @PutMapping(value = "/meta", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateUserMeta(@RequestBody UserMetaDto userMetaDto){
        return ResponseEntity.ok(userMetaService.updateUserMeta(userMetaDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ, ROLE_USER.READ")
    @GetMapping(value = "/meta/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getUserMetaById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userMetaService.getUserMetaById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ, ROLE_USER.READ")
    @GetMapping(value = "/meta/{userid}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getUserMetaByUserId(@PathVariable("userid") Long id){
        return ResponseEntity.ok(userMetaService.getUserMetaByUserId(id));
    }

}
