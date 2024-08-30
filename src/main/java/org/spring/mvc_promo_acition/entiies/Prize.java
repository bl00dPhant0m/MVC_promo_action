package org.spring.mvc_promo_acition.entiies;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prizes")
@AllArgsConstructor
@NoArgsConstructor

public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String nameOfPrize;


    private String code;

    private String path;

    @NotNull(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "^([A-Za-zА-Яа-я]+\\s){2}[A-Za-zА-Яа-я]+$", message = "ФИО должно состоять из трех слов и содержать только буквы")
    private String fullNameOfWinner;

    @NotNull(message = "Номер телефона не должен быть пустым")
    @Min(value = 89000000000L, message = "Номер телефона должен быть валидным")
    @Max(value = 89999999999L, message = "Номер телефона должен быть  валидным")
    private long telephoneNumberOfWinner;

    @Email(message = "Email должен быть валидным")
    @NotNull(message = "Email не должен быть пустым")
    private String emailOfWinner;

    private boolean status;

    public long getId() {
        return id;
    }

    public String getNameOfPrize() {
        return nameOfPrize;
    }

    public String getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getFullNameOfWinner() {
        return fullNameOfWinner;
    }

    public long getTelephoneNumberOfWinner() {
        return telephoneNumberOfWinner;
    }

    public String getEmailOfWinner() {
        return emailOfWinner;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNameOfPrize(String nameOfPrize) {
        this.nameOfPrize = nameOfPrize;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFullNameOfWinner(String fullNameOfWinner) {
        this.fullNameOfWinner = fullNameOfWinner;
    }

    public void setTelephoneNumberOfWinner(long telephoneNumberOfWinner) {
        this.telephoneNumberOfWinner = telephoneNumberOfWinner;
    }

    public void setEmailOfWinner(String emailOfWinner) {
        this.emailOfWinner = emailOfWinner;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
