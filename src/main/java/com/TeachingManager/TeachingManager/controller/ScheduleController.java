package com.TeachingManager.TeachingManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScheduleController {

    @GetMapping("/Schedule")
    public String Lecture(){
        return "schedule/schedule_main";
    }

    @PostMapping("/Schedule/Delete")
    public String Delete_Lecture() {
        
//        삭제 동작
        
        return "redirect:/";
    }
}
