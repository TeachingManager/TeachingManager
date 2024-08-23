package com.TeachingManager.TeachingManager.controller.View;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewAccountController {

    @GetMapping("/newAccountAuthentication")
    public String accountSendPage(
            @RequestParam("email") String email, Model model
    ) {
        model.addAttribute("email", email);
        return "newAccountAuthentication";
    }
}
