package org.spring.mvc_promo_acition.service;


import org.spring.mvc_promo_acition.model.entiies.Admin;

public interface AdminService {
    Admin saveAdmin(Admin admin);
    Admin getAdminByName(String adminName);
}
