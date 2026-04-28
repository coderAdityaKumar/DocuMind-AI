package com.aditya.documind.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.documind.DTO.UserResponse;
import com.aditya.documind.Entity.Organization;
import com.aditya.documind.Entity.User;
import com.aditya.documind.Repository.OrganizationRepo;
import com.aditya.documind.Repository.UserRepo;
import com.aditya.documind.Service.UserService;
import com.aditya.documind.Tenant.TenantContext;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepo;
    private final OrganizationRepo organizationRepo;
    private final TenantContext tenantContext;
    private final UserService userService;

    public UserController(UserRepo userRepo, OrganizationRepo organizationRepo,TenantContext tenantContext,UserService userService) {
        this.userRepo = userRepo;
        this.organizationRepo = organizationRepo;
        this.tenantContext=tenantContext;
        this.userService=userService;
        
    }

    @GetMapping("/tenant-check")
    public String tenantCheck(){
        return "Tenant ID : "+tenantContext.getTenantId();
    }

    @PostMapping("/org")
    public Organization createOrg(@RequestBody Organization org) {
        return organizationRepo.save(org);
    }

    @PostMapping
    public User createUser(@RequestBody User user, @RequestParam Long orgId) {
        Organization org = organizationRepo.findById(orgId).orElseThrow(() -> new RuntimeException("Org not found"));
        user.setOrganization(org);
        return userRepo.save(user);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepo.findAll();

        return userService.getAllUsers();
    }

    
}
