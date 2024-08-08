package com.TeachingManager.TeachingManager.controller.View.Institute;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstituteController {
    @GetMapping("/login")
    public String login() {
        return "Institute/login-institute";
    }

    @GetMapping("/signup/institute")
    public String signup() {
        return "Institute/signup-institute";
    }
}
