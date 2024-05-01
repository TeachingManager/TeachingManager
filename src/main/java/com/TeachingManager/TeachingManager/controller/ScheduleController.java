package com.TeachingManager.TeachingManager.controller;

import com.TeachingManager.TeachingManager.Forms.ScheduleForm;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleServiceImpl;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("schedules", scheduleService.search_all_marker());
        return "schedule/schedule_main";
    }


    /*     일정 추가   */
    @GetMapping("/Schedule/Create")
    public String Create_Schedule() {
        return "schedule/schedule_create";
    }

    @PostMapping("/Schedule/Create")
    public String New_Schedule(ScheduleForm form) {
        Schedule sc = new Schedule();

        sc.setTitle(form.getTitle());
        sc.setStart_date(form.getStart_date());
        sc.setEnd_date(form.getEnd_date());
        sc.setMemo(form.getMemo());

        scheduleService.create_schedule(sc);

        return "redirect:/Schedule";
    }


   /* 강의 가져오기 */
   @GetMapping("/Schedule/Import")
   public String Select_Lecture_for_Schedule() {
       return "schedule/schedule_import";
   }

    @PostMapping("/Schedule/Import")
    public String Import_Schedule() {
        return "redirect:/Schedule";
    }


    /*     일정 디테일    */
    @GetMapping("/Schedule/Detail")
    public String Detail_Schedule(@RequestParam("pk") Long pk, Model model) {

        Schedule sc = scheduleService.search_schedule(pk);

        model.addAttribute("title", sc.getTitle());
        model.addAttribute("start_date", sc.getStart_date());
        model.addAttribute("end_date", sc.getEnd_date());
        model.addAttribute("memo", sc.getMemo());

        return "schedule/schedule_detail/" + pk;
    }




    /*     일정 수정    */
    @GetMapping("/Schedule/Update")
    public String Update_Schedule(@RequestParam("pk") Long pk, Model model) {

        Schedule sc = scheduleService.search_schedule(pk);

        model.addAttribute("title", sc.getTitle());
        model.addAttribute("start_date", sc.getStart_date());
        model.addAttribute("end_date", sc.getEnd_date());
        model.addAttribute("memo", sc.getMemo());

        return "schedule/schedule_update" + pk;
    }

    @PostMapping("/Schedule/Update")
    public String Revise_Schedule(ScheduleForm form) {
        Schedule sc = new Schedule();

        sc.setTitle(form.getTitle());
        sc.setStart_date(form.getStart_date());
        sc.setEnd_date(form.getEnd_date());
        sc.setMemo(form.getMemo());

        scheduleService.update_schedule(sc);

        return "redirect:/Schedule";
    }




    /*     일정 삭제   */
    @PostMapping("/Schedule/Delete")
    public String Delete_Schedule(@RequestParam("pk") Long pk) {
        scheduleService.delete_schedule(pk);
        return "redirect:/Schedule";
    }

}
