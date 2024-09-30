package org.spring.mvc_promo_acition.service;

import lombok.RequiredArgsConstructor;
import org.spring.mvc_promo_acition.model.entiies.Admin;
import org.spring.mvc_promo_acition.repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin getAdminByName(String adminName) {
        return adminRepository.findByUsername(adminName).orElse(null);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }
}
