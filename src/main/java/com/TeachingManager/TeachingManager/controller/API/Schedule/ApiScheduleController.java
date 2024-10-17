package com.TeachingManager.TeachingManager.controller.API.Schedule;

import com.TeachingManager.TeachingManager.DTO.Schedule.*;
import com.TeachingManager.TeachingManager.Service.Schedule.ScheduleService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApiScheduleController {

    private final ScheduleService scheduleService;

    /*  일정 추가 api  */
    @PostMapping("/api/Schedule")
    public ResponseEntity<Schedule> Create_Schedule(@AuthenticationPrincipal CustomUser user, @RequestBody AddScheduleRequest request) {
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
                return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create_schedule(request, user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 자기 자신학원, 또는 소속되어있는 학원의 특정 달 정보를 가져온다. (이번달)
    @GetMapping("/api/Schedule")
    public ResponseEntity<MonthScheduleResponse> Schedule(@AuthenticationPrincipal CustomUser user, @RequestBody MonthScheduleRequest request){
        System.out.println("request = " + request.getDate_info());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.searchAll_scheduleByDate(user, request.getDate_info()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//  자기 자신학원, 또는 소속되어있는 학원의 하루 일정을 가져오는 API
    @GetMapping("/api/Schedule/day")
    public ResponseEntity<DayScheduleResponse> OneDaySchedule(@AuthenticationPrincipal CustomUser user, @RequestBody MonthScheduleRequest request){
        System.out.println("request = " + request.getDate_info());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.searchAll_scheduleByDay(user, request.getDate_info()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*     일정 디테일    */
    @GetMapping("/api/Schedule/{pk}")
    public ResponseEntity<ScheduleResponse> Detail_Schedule(@PathVariable("pk") Long pk,@AuthenticationPrincipal CustomUser user ) {
        if (user != null) {
            return ResponseEntity.ok()
                    .body(new ScheduleResponse(scheduleService.search_schedule(user, pk)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*  일정 삭제 api  */
    @PutMapping("/api/delete/Schedule/{pk}")
    public ResponseEntity<String> Delete_Schedule(@PathVariable("pk") Long pk, @AuthenticationPrincipal CustomUser user) {
        // 학원 권한을 가진 쪽에서 요청인지
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            scheduleService.delete_schedule(user, pk);
            return ResponseEntity.ok().build();
        }
        // 아니라면 거절
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*  일정 수정 api  */
    @PutMapping("/api/Schedule/{pk}")
    public ResponseEntity<ScheduleResponse> Revise_Schedule(@PathVariable("pk") Long pk, @RequestBody UpdateScheduleRequest request, @AuthenticationPrincipal CustomUser user) {
        // 학원 권한을 가진 쪽에서 요청이 들어왔는가
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            Schedule sc = scheduleService.update_schedule(user, pk, request);
            return ResponseEntity.ok()
                    .body(new ScheduleResponse(sc));
        }
        // 학원에서 들어오 요청이 아니라면.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
}

