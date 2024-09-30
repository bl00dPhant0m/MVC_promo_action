package org.spring.mvc_promo_acition.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/authorization")
    public String authorization() {
        return "/admin/authorization";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль.");
        }
        return "admin/authorization";  // Возвращаем имя Thymeleaf шаблона для страницы авторизации
    }


}
