package com.bkbwongo.core.ebaasa.walletmgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.security.JwtUtil;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import com.bkbwongo.core.ebaasa.walletmgt.dto.WalletGroupDto;
import com.bkbwongo.core.ebaasa.walletmgt.service.WalletGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 12/06/2021
 * @project ebaasa-sms
 */
@Tag(name = "Wallet Groups", description = "Manage wallet groups")
@RestController
@RequestMapping("/api")
public class WalletGroupApi {

    @Autowired
    private WalletGroupService walletGroupService;

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/wallet/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addWalletGroup(@RequestBody WalletGroupDto walletGroupDto){
        return ResponseEntity.ok(walletGroupService.createWalletGroup(walletGroupDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/wallet/group", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateWalletGroup(@RequestBody WalletGroupDto walletGroupDto){
        return ResponseEntity.ok(walletGroupService.updateWalletGroup(walletGroupDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallet/group/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWalletGroupById(@PathVariable("id") Long id){
        return ResponseEntity.ok(walletGroupService.getWalletGroupById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallet/group/{name}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getWalletGroupByName(@PathVariable("name") String name){
        return ResponseEntity.ok(walletGroupService.getWalletGroupByName(name));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/wallet/groups", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllWalletGroup(){
        return ResponseEntity.ok(walletGroupService.getAllWalletGroups());
    }
}
