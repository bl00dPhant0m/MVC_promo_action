package org.spring.mvc_promo_acition.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PrizeForAdmin {
   // @NotEmpty(message = "Название приза не должно быть пустым")
    private String nameOfPrize;

   // @NotEmpty(message = "Поле с кодами не должно быть пустым")
    private String codes;

    //@NotNull(message = "Файл не должен быть пустым")
    private MultipartFile file;
}
