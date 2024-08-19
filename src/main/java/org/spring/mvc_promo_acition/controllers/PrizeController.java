package org.spring.mvc_promo_acition.controllers;

import org.spring.mvc_promo_acition.entiies.Prize;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.spring.mvc_promo_acition.service.PrizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

@Controller
public class PrizeController {
    private PrizeService prizeService;
    private final PrizeRepository prizeRepository;

    public PrizeController(PrizeRepository prizeRepository, PrizeService prizeService) {
        this.prizeRepository = prizeRepository;
        this.prizeService = prizeService;
    }

    @GetMapping(value = "/add_prize")
    public String addPrizeGet() {
        return "admin/add_prize";
    }

    @PostMapping(value = "/add_prize")
    public String addPrizePost(@RequestParam String nameOfPrize,
                               @RequestParam String codes,
                               @RequestParam MultipartFile file,
                               Model model) {
        Path path = prizeService.getPath(file);
        prizeService.saveImg(path, file);


        String[] codesArray = codes.split("[ ,;]+");
        String pathImg = prizeService.pathToString(file);
        System.out.println(pathImg);

        List<Prize> prizes = prizeService.createPrizes(nameOfPrize, codesArray,pathImg);
        prizeService.saveSomePrizes(prizes);

        model.addAttribute("successMessage", "Приз добавлен!");

        return "admin/add_prize";
    }

    @GetMapping(value = "/admin_panel")
    public String adminPanelGet() {
        return "admin/admin_panel";
    }

    @GetMapping(value = "/winners/{sort}")
    public String winnersGet(@PathVariable String sort,
                             Model model) {

        //вынести в сервис
        List<Prize> prizes = prizeRepository.findAll();
        if (sort.equals("name")) {
            prizes = prizes.stream()
                    .sorted(Comparator.comparing(Prize::getNameOfPrize))
                    .toList();
        } else if (sort.equals("status")) {
            prizes = prizes.stream()
                    .sorted(Comparator.comparing(Prize::isStatus))
                    .toList();
        }
        model.addAttribute("prizes", prizes);
        return "admin/winners";
    }

    @PostMapping(value = "/edit_status")
    public String editStatusOfPrize(@RequestParam long id) {
        prizeService.editStatusById(id);
        return "redirect:/winners";
    }

    @GetMapping(value = "/")
    public String startPageGet() {
        return "user/";
    }

    @GetMapping(value = "/promo-code")
    public String promoCodeGet() {
        return "user/promo-code";
    }

    //


}
