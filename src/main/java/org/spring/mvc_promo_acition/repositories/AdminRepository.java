package org.spring.mvc_promo_acition.repositories;

import org.spring.mvc_promo_acition.model.entiies.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByUsername(String username);
}
