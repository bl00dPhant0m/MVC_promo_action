package org.spring.mvc_promo_acition.controllers;

import org.spring.mvc_promo_acition.entiies.Prize;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.spring.mvc_promo_acition.service.PrizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
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


        String[] codesArray = codes.split(";");
        String pathImg = prizeService.pathToString(file);
        System.out.println(pathImg);

        List<Prize> prizes = prizeService.createPrizes(nameOfPrize, codesArray,pathImg);
        prizeService.saveSomePrizes(prizes);

        return "redirect:/add_prize";
    }

    @GetMapping(value = "/admin_panel")
    public String adminPanelGet() {
        return "admin/admin_panel";
    }

    @GetMapping(value = "/winners")
    public String winnersGet(Model model) {
        List<Prize> prizes = prizeRepository.findAll();
        model.addAttribute("prizes", prizes);
        return "admin/winners";
    }

    @PostMapping(value = "/edit_status")
    public String editStatusOfPrize(@RequestParam long id) {
        prizeService.editStatusById(id);
        return "redirect:/winners";
    }
}
