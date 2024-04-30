package com.TeachingManager.TeachingManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/Schedule")
    public String Lecture(){
        return "schedule/schedule_main";
    }
}
