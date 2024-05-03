package com.TeachingManager.TeachingManager.controller.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApiScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ApiScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /*  일정 삭제 api  */
    @PostMapping("/api/Schedule/Detail/{pk}")
    public ResponseEntity<Void> Delete_Schedule(@PathVariable Long pk) {
        scheduleService.delete_schedule(pk);
        return ResponseEntity.ok().build();
    }

    /*  일정 수정 api  */
    @PutMapping("/api/Schedule/Detail/{id}")
    public String Revise_Schedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        Schedule sc = scheduleService.update_schedule(id, request);
        return "redirect:/Schedule/?pk=" + sc.getSchedule_id();
    }

    /*  일정 추가 api  */
    @PostMapping("/api/Schedule")
    public ResponseEntity<Schedule> Create_Schedule(@RequestBody AddScheduleRequest request) {
        Schedule sc = scheduleService.create_schedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(sc);
    }


}

