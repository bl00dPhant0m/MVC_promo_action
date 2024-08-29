package org.spring.mvc_promo_acition.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class RulesController {

    @GetMapping(value = "/rules")
    public void rulesGet(HttpServletResponse res) {

        String path = "files/rules.pdf";
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            InputStream is = classPathResource.getInputStream();
            res.setContentType("application/pdf");
            res.setHeader("Content-Disposition","inline; filename=rules.pdf");
            byte[] pdfBytes = is.readAllBytes();
            res.getOutputStream().write(pdfBytes);
            res.getOutputStream().flush();
            res.getOutputStream().close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
