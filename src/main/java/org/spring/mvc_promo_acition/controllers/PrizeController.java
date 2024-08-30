package org.spring.mvc_promo_acition.controllers;

import jakarta.validation.Valid;
import org.spring.mvc_promo_acition.dto.PrizeDTO;
import org.spring.mvc_promo_acition.dto.PrizeForAdmin;
import org.spring.mvc_promo_acition.dto.PrizeForUser;
import org.spring.mvc_promo_acition.entiies.Prize;
import org.spring.mvc_promo_acition.repositories.PrizeRepository;
import org.spring.mvc_promo_acition.service.PrizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String addPrizeGet(Model model) {
        model.addAttribute("prizeForAdmin", new PrizeForAdmin());
        return "admin/add_prize";
    }

    @PostMapping(value = "/add_prize")
    public String addPrizePost(@Valid @ModelAttribute("prizeForAdmin") PrizeForAdmin prize,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("prizeForAdmin", prize);
            return "admin/add_prize";
        }
        MultipartFile file = prize.getFile();
        Path path = prizeService.getPath(file);
        prizeService.saveImg(path, file);


        String[] codesArray = prizeService.parseCodes(prize.getCodes());
        String pathImg = prizeService.pathToString(file);

        List<Prize> prizes = prizeService.createPrizes(prize.getNameOfPrize(), codesArray,pathImg);
        prizeService.saveSomePrizes(prizes);

        model.addAttribute("successMessage", "Приз добавлен!");

        return "admin/add_prize";
    }

    @GetMapping(value = "/admin_panel")
    public String adminPanelGet() {
        return "admin/admin_panel";
    }

    @GetMapping(value = "/winners")
    public String winnersGet(@RequestParam(required = false) String sort,
                             Model model) {
        var prizes = prizeService.sortPrizes(sort);
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

    @GetMapping(value = "/prizes")
    public String prizesGet(Model model) {

        List<PrizeDTO> dtoPrizes = prizeService.getAllUniquePrizes();
        model.addAttribute("prizes", dtoPrizes);
        return "user/prizes";
    }

    @GetMapping(value = "/promo-form")
    public String checkPromoGet(@RequestParam String promoCode,
                                Model model) {

        if(promoCode == null){
            model.addAttribute("info", "Поле не должно быть пустым.");
            return "admin/promo-code";
        }

        Prize prize = prizeService.getPrizeByPromoCode(promoCode);
        if(prize == null){
            model.addAttribute("info", "Код не действительный.");
            return "user/promo-code";
        }

        if (prize.getFullNameOfWinner() != null){
            model.addAttribute("info", "Код уже активирован.");
            return "user/promo-code";
        }



        return "redirect:/form-for-winners?code=" + prize.getCode();
    }

    @GetMapping(value = "/form-for-winners")
    public String formForWinnersGet(@RequestParam("code") String code,
                                    Model model) {
        Prize prize = prizeService.getPrizeByPromoCode(code);
        model.addAttribute("prize", prize);
        return "user/form-for-winners";
    }




    @PostMapping(value = "/form-for-winners")
    public String formForWinnersPost(@Valid @ModelAttribute("prize") Prize prize,
                                     BindingResult bindingResult,
                                     Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("prize", prize);
            return "user/form-for-winners";
        }

        prizeService.saveOnePrize(prize);
        model.addAttribute("name", prize.getFullNameOfWinner());
        model.addAttribute("phone", prize.getTelephoneNumberOfWinner());

        return "user/info";
    }

    @GetMapping(value = "/info")
    public String infoGet() {
        return "user/info";
    }
}
