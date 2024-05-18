package com.TeachingManager.TeachingManager.controller.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

//   강의 목록(일정홈)  강의 가져오기

    /*     일정 홈   */
    @GetMapping("/Schedule")
    public String Schedule(Model model){
        model.addAttribute("schedules", scheduleService.searchAll_schedule());
        return "schedule/schedule_main";
    }

    /*     일정 디테일    */
    @GetMapping("/Schedule/Detail")
    public String Detail_Schedule(@RequestParam("pk") Long pk, Model model) {

        ScheduleResponse response = new ScheduleResponse(scheduleService.search_schedule(pk).orElseThrow());

        model.addAttribute("schedule", response);

        return "schedule/schedule_detail";
    }


//    /*  일정  작성 폼  */
//    @GetMapping("/Schedule/form")
//    public String Create_Schedule() {
//        return "schedule/schedule_form";
//    }


    /*     일정  폼   */
    @GetMapping("/Schedule/form")
    public String Update_Schedule(@RequestParam(required = false, name = "pk") Long pk, Model model) {

        if (pk == null){
            model.addAttribute("schedule", new ScheduleResponse());
        }
        else {
            ScheduleResponse response = new ScheduleResponse(scheduleService.search_schedule(pk).orElseThrow());
            model.addAttribute("schedule", response);
        }

        return "schedule/schedule_form";
    }


//   /* 강의 가져오기 */
//   @GetMapping("/Schedule/Import")
//   public String Select_Lecture_for_Schedule() {
//       return "schedule/schedule_import";
//   }
//
//    @PostMapping("/Schedule/Import")
//    public String Import_Schedule() {
//        return "redirect:/Schedule";
//    }
    
}