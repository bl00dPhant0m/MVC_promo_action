package org.spring.mvc_promo_acition.model.entiies;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(unique = true, nullable = false)
   private String username;
   private String password;

   @Enumerated(EnumType.STRING)
   private Role role;
}
