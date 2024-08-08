package com.TeachingManager.TeachingManager.controller.API.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.AddScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.Schedule;
import com.TeachingManager.TeachingManager.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApiScheduleController {

    private final ScheduleService scheduleService;
    private final TokenService tokenService;


    /*  일정 삭제 api  */
    @PostMapping("/api/Schedule/Detail/{pk}")
    public ResponseEntity<Void> Delete_Schedule(@PathVariable("pk") Long pk) {
        scheduleService.delete_schedule(pk);
        return ResponseEntity.ok().build();
    }

    /*  일정 수정 api  */
    @PutMapping("/api/Schedule/Detail/{pk}")
    public String Revise_Schedule(@PathVariable("pk") Long pk, @RequestBody UpdateScheduleRequest request) {
        Schedule sc = scheduleService.update_schedule(pk, request);
        return "redirect:/Schedule/?pk=" + sc.getSchedule_id();
    }

    /*  일정 추가 api  */
    @PostMapping("/api/Schedule")
    public ResponseEntity<Schedule> Create_Schedule(@RequestHeader("Authorization") String authorizationHeader,@RequestBody AddScheduleRequest request) {
        Schedule sc = scheduleService.create_schedule(request, tokenService.findPKInHeaderToken(authorizationHeader));

        if (sc != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(sc);
        }
        else{
            // 존재하지 않는 이메일로 들어왔을 경우.
            return (ResponseEntity<Schedule>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }
}

