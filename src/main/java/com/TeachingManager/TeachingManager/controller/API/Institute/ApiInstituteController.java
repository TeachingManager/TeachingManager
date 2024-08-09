package com.TeachingManager.TeachingManager.controller.API.Institute;

import com.TeachingManager.TeachingManager.DTO.Institute.AddInstituteRequest;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ApiInstituteController {
    private final InstituteServiceImpl instService;

    @PostMapping("/institute")
    public String signup(AddInstituteRequest request){
        instService.register(request);
        return "redirect:/login";
    }

    @GetMapping("/logout/institute")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login/institute";
    }
}
