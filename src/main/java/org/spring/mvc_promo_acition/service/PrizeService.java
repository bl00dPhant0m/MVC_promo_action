package org.spring.mvc_promo_acition.service;

import jakarta.transaction.Transactional;
import org.spring.mvc_promo_acition.entiies.DTOPrize;
import org.spring.mvc_promo_acition.entiies.Prize;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrizeService {
    private PrizeRepository prizeRepository;
    public PrizeService(PrizeRepository prizeRepository) {
        this.prizeRepository = prizeRepository;
    }

    public List<Prize> createPrizes(String name, String [] promoCodes, String pathImg) {
        List<Prize> prizes = new ArrayList<>();


        for (String promoCode : promoCodes) {
            Prize prize = new Prize();
            prize.setNameOfPrize(name);
            prize.setPath(pathImg);
            prize.setCode(promoCode);
            prizes.add(prize);
        }
        return prizes;
    }

    public Path getPath(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/img/"+fileName);
        return path;
    }


    public void saveImg(Path path, MultipartFile file) {
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String pathToString(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return "/img/" + fileName;
    }

    public List<DTOPrize> convertPrizesToDTO(List<Prize> prizes) {
//        List<DTOPrize> dtoPrizes = new ArrayList<>();
//        for (Prize prize : prizes) {
//            DTOPrize dtoPrize = new DTOPrize();
//            dtoPrize.setNameOfPrize(prize.getNameOfPrize());
//            dtoPrize.setPath(prize.getPath());
//            dtoPrizes.add(dtoPrize);
//        }
//        return dtoPrizes;
//
//
        return prizes.stream()
                .map(prize -> new DTOPrize(prize.getCode(), prize.getNameOfPrize()))
                .toList();


    }

    public void saveSomePrizes(List<Prize> prizes) {
       prizeRepository.saveAll(prizes);
    }

//    public void editStatusById(long id) {
//        Optional<Prize> byId = prizeRepository.findById(id);
//        if (byId.isPresent()) {
//            Prize prize = byId.get();
//            prize.setStatus(!prize.isStatus());
//            prizeRepository.save(prize);
//        }
//    }

    @Transactional
    public void editStatusById(long id) {
        prizeRepository.updatePrizeStatusById(id);
    }
}
