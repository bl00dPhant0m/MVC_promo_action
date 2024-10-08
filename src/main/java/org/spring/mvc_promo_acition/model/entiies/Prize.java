package org.spring.mvc_promo_acition.model.entiies;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    //@NotNull(message = "ФИО не должно быть пустым")
    //@Pattern(regexp = "^([A-Za-zА-Яа-я]+\\s){2}[A-Za-zА-Яа-я]+$", message = "ФИО должно состоять из трех слов и содержать только буквы")
    private String fullNameOfWinner;

    //@NotNull(message = "Номер телефона не должен быть пустым")
    //@Min(value = 89000000000L, message = "Номер телефона должен быть валидным")
    //@Max(value = 89999999999L, message = "Номер телефона должен быть  валидным")
    private long telephoneNumberOfWinner;

    //@Email(message = "Email должен быть валидным")
    //@NotNull(message = "Email не должен быть пустым")
    private String emailOfWinner;

    private boolean status;


}
