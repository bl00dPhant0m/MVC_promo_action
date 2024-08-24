package org.spring.mvc_promo_acition.service;

import jakarta.transaction.Transactional;
import org.spring.mvc_promo_acition.dto.PrizeDTO;
import org.spring.mvc_promo_acition.entiies.Prize;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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

    public List<PrizeDTO> convertPrizesToDTO(List<Prize> prizes) {
        return prizes.stream()
                .map(prize -> new PrizeDTO(prize.getCode(), prize.getNameOfPrize()))
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

    public Prize getPrizeByPromoCode(String promoCode) {
        return prizeRepository.findByCode(promoCode);
    }

    @Transactional
    public List<PrizeDTO> getAllUniquePrizes(){
        return prizeRepository.findAllUniquePrizes();
    }

    @Transactional
    public void editStatusById(long id) {
        prizeRepository.updatePrizeStatusById(id);
    }

    public List<Prize> sortPrizes(String sort) {
        List<Prize> prizes = prizeRepository.findAll();
        if(sort != null){
            if ("name".equals(sort)) {
                prizes = prizes.stream()
                        .sorted(Comparator.comparing(Prize::getNameOfPrize))
                        .toList();
            } else if ("status".equals(sort)) {
                prizes = prizes.stream()
                        .sorted(Comparator.comparing(Prize::isStatus).reversed())
                        .toList();
            }
        }
        return prizes;
    }

    public Prize getPrizeById(long id) {
        return prizeRepository.findById(id).get();
    }

    public void saveOnePrize(Prize prize,
                             int prizePhone,
                             String prizeEmail,
                             String prizeNameOfWinner) {
        prize.setEmailOfWinner(prizeEmail);
        prize.setNameOfPrize(prizeNameOfWinner);
        prize.setTelephoneNumberOfWinner(prizePhone);
        prizeRepository.save(prize);
    }
}
