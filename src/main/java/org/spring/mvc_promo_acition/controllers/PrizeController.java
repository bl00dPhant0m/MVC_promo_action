package org.spring.mvc_promo_acition.controllers;

import org.spring.mvc_promo_acition.dto.PrizeDTO;
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
            //добавить сообщение что пустой код
            return "admin/promo-code";
        }

        Prize prize = prizeService.getPrizeByPromoCode(promoCode);
        if(prize == null){
            //добавить сообщение код не действительный
            return "admin/promo-code";
        }

        if (prize.getFullNameOfWinner() != null){
            //коментарий код уже активирован
            return "admin/promo-code";
        }
        model.addAttribute("prizeID", prize.getId());
        model.addAttribute("prizeName", prize.getNameOfPrize());
        model.addAttribute("prizeImagePath", prize.getPath());

        return "user/form-for-winners";
    }

    @PostMapping("/form-for-winners")
    public String formForWinnersPost(@RequestParam long prizeId,
                                     @RequestParam int prizePhone,
                                     @RequestParam String prizeEmail,
                                     @RequestParam String prizeNameOfWinner,
                                     Model model){
        Prize prize = prizeService.getPrizeById(prizeId);
        prizeService.saveOnePrize(prize, prizePhone, prizeEmail, prizeNameOfWinner);
        model.addAttribute("name", prizeNameOfWinner);
        model.addAttribute("phone", prizePhone);

        return "user/return";
    }
    //


}
