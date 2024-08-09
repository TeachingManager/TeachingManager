package com.TeachingManager.TeachingManager.controller.API.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.*;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApiScheduleController {

    private final ScheduleService scheduleService;
    private final TokenService tokenService;

    /*  일정 추가 api  */
    @PostMapping("/api/Schedule")
    public ResponseEntity<Schedule> Create_Schedule(@AuthenticationPrincipal CustomUser user, @RequestBody AddScheduleRequest request) {
        Schedule sc = scheduleService.create_schedule(request, user.getPk());

        if (sc != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(sc);
        }
        else{
            // 존재하지 않는 이메일로 들어왔을 경우.
            return (ResponseEntity<Schedule>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    // 자기 자신학원, 또는 소속되어있는 학원의 특정 달 정보를 가져온다. (이번달)
    @GetMapping("/api/Schedule")
    public ResponseEntity<MonthScheduleResponse> Schedule(@AuthenticationPrincipal CustomUser user, @RequestBody MonthScheduleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.searchAll_scheduleByDate(user, request.getDate_info()));
    }

    /*     일정 디테일    */
    @GetMapping("/api/Schedule/{pk}")
    public ResponseEntity<ScheduleResponse> Detail_Schedule(@PathVariable("pk") Long pk,@AuthenticationPrincipal CustomUser user ) {
        ScheduleResponse response = new ScheduleResponse(scheduleService.search_schedule(user, pk));
        return ResponseEntity.ok()
                .body(response);

    }

    /*  일정 삭제 api  */
    @PostMapping("/api/delete/Schedule/{pk}")
    public ResponseEntity<Void> Delete_Schedule(@PathVariable("pk") Long pk, @AuthenticationPrincipal CustomUser user) {
        scheduleService.delete_schedule(user, pk);
        return ResponseEntity.ok().build();
    }

    /*  일정 수정 api  */
    @PutMapping("/api/Schedule/{pk}")
    public ResponseEntity<ScheduleResponse> Revise_Schedule(@PathVariable("pk") Long pk, @RequestBody UpdateScheduleRequest request, @AuthenticationPrincipal CustomUser user) {
        Schedule sc = scheduleService.update_schedule(user, pk, request);
        return ResponseEntity.ok()
                .body(new ScheduleResponse(sc));

    }


}

