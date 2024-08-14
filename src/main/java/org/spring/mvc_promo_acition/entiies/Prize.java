package org.spring.mvc_promo_acition.entiies;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "prizes")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameOfPrize;

    private String code;

    private String path;

    private String fullNameOfWinner;

    private int telephoneNumberOfWinner;

    private String emailOfWinner;

    private boolean status;



}
