package org.spring.mvc_promo_acition.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.mvc_promo_acition.model.entiies.Admin;
import org.spring.mvc_promo_acition.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestAdminController {
    private final AdminService adminService;

    @PostMapping("/add-admin")
    public String addAdmin(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return "admin saved";
    }
}
