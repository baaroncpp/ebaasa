package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.usermgt.service.UserRolePermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bkaaron
 * @created on 15/05/2021
 * @project ebaasa-sms
 */
@RestController
@RequestMapping("api/v1/user/permission")
public class UserRolePermissionGroupApi {

    @Autowired
    private UserRolePermissionGroupService userRolePermissionGroupService;
}
