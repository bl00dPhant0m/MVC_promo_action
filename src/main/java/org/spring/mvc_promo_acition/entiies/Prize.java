package org.spring.mvc_promo_acition.entiies;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

//    @NotNull(message = "ФИО не должно быть пустым")
//    @Pattern(regexp = "^([A-Za-zА-Яа-я]+\\s){2}[A-Za-zА-Яа-я]+$", message = "ФИО победителя должно состоять из трех слов и содержать только буквы")
    private String fullNameOfWinner;

//    @NotNull(message = "Номер телефона не должен быть пустым")
//    @Pattern(regexp = "^[0-9]{11}$", message = "Номер телефона должен состоять из 11 цифр")
    private long telephoneNumberOfWinner;

//    @Email(message = "Email должен быть валидным")
    private String emailOfWinner;

    private boolean status;



}
