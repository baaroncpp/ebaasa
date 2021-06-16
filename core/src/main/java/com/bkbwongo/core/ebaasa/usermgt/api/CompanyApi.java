package com.bkbwongo.core.ebaasa.usermgt.api;

import com.bkbwongo.core.ebaasa.base.api.BaseAPI;
import com.bkbwongo.core.ebaasa.usermgt.dto.CompanyDto;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import com.bkbwongo.core.ebaasa.usermgt.service.CompanyService;
import com.bkbwongo.core.ebaasa.usermgt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 22/05/2021
 * @project ebaasa-sms
 */
@Tag(name = "Users", description = "Manage all user")
@RestController
@RequestMapping("/api")
public class CompanyApi {

    private CompanyService companyService;
    private UserService userService;

    private static final String SORT_DESC = "DESCENDING";
    private static final String SORT_ASC = "ASCENDING";
    private static final String SORT_BY = "createdOn";

    @Autowired
    public CompanyApi(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @RolesAllowed("ROLE_ADMIN.WRITE")
    @PostMapping(value = "/company", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> addCompany(@RequestBody CompanyDto companyDto){
        return ResponseEntity.ok(companyService.addCompany(companyDto));
    }

    @RolesAllowed("ROLE_ADMIN.UPDATE")
    @PutMapping(value = "/company", consumes = BaseAPI.APPLICATION_JSON, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> updateCompany(@RequestBody CompanyDto companyDto){
        String username = "";
        Optional<TUser> user = userService.getUserByUsername(username);
        return ResponseEntity.ok(companyService.updateCompany(user.get(), companyDto));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/company/{id}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getCompanyById(@PathVariable("id") Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/company/{name}", produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getCompanyByBusinessName(@PathVariable("name") String businessName){
        return ResponseEntity.ok(companyService.getCompanyByBusinessName(businessName));
    }

    @RolesAllowed("ROLE_ADMIN.READ")
    @GetMapping(value = "/companies", params = { "page", "size" ,"sort"}, produces = BaseAPI.APPLICATION_JSON)
    public ResponseEntity<Object> getAllCompanies(@RequestParam("page") int page,
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
        return ResponseEntity.ok(companyService.getAllCompanies(pageable));

    }

}
