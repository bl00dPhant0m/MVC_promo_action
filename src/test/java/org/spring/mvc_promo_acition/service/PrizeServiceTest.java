package org.spring.mvc_promo_acition.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PrizeServiceTest {

    @Mock
    private PrizeRepository prizeRepository;
    private PrizeService prizeService = new PrizeService(prizeRepository);

    @Mock
    private MultipartFile multipartFile;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getPath() {
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("avatar.jpg");

        Path nameFile = prizeService.getPath(multipartFile);
        assertNotNull(nameFile);
        assertEquals(Path.of("src/main/resources/static/img/avatar.jpg"), nameFile);

        String path = "src/main/resources/static/img/avatar.jpg";
        String pathImg = nameFile.toString().replace("\\","/");
        assertEquals(path, pathImg);
    }
}