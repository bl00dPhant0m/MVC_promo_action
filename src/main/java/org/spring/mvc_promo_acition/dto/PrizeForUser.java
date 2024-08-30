package org.spring.mvc_promo_acition.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrizeForUser {
    @NotNull(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "^([A-Za-zА-Яа-я]+\\s){2}[A-Za-zА-Яа-я]+$", message = "ФИО должно состоять из трех слов и содержать только буквы")
    private String prizeNameOfWinner;

    @NotNull(message = "Номер телефона не должен быть пустым")
    @Min(value = 89000000000L, message = "Номер телефона должен быть валидным")
    @Max(value = 89999999999L, message = "Номер телефона должен быть  валидным")
    private long prizePhone;

    @Email(message = "Email должен быть валидным")
    @NotNull(message = "Email не должен быть пустым")
    private String prizeEmail;
}
