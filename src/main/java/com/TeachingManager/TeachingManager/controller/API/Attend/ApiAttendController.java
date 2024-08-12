package com.TeachingManager.TeachingManager.controller.API.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.AttendInfo;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthLectureAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthStudentAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.Request.UpdateAttendListRequest;
import com.TeachingManager.TeachingManager.Service.Attend.AttendService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiAttendController {

    private final AttendService attendService;


    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////

    // 단일 출석에 관한 정보를 가져오는 api. 대게 각 출석의 메모를 가져오는데 사용될 것 같긴하다.
    @PostMapping("/api/attend/{pk}")
    public ResponseEntity<AttendInfo> Detail_Attend(@PathVariable("pk") Long pk, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok().body(attendService.searchById(user, pk));
    }

    // 특정 달의 특정 강의의 출석 리스트
    @GetMapping("/api/attend/lecture")
    public ResponseEntity<List<MonthLectureAttendResponse>>findMonthlyLectureAttendance(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(value = "lecture_id") Long lecture_id,
            @RequestParam(value = "date_info") LocalDate date_info
    ) {
        return ResponseEntity.ok().body(attendService.findMonthlyLectureAttendance(user, lecture_id, date_info));
    }

    // 특정 달의 특정 학생의 출석 리스트
    @GetMapping("/api/attend/student")
    public ResponseEntity<List<MonthStudentAttendResponse>> findMonthlyStudentAttendance(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(value = "student_id") Long student_id,
            @RequestParam(value = "date_info") LocalDate date_info
    ) {
        return ResponseEntity.ok().body(attendService.findMonthlyStudentAttendance(user, student_id, date_info));
    }


    //////////////////////////////////////////////////////////
    ///                       생성                           //
    //////////////////////////////////////////////////////////
    // 특정일정에 대한 출석 정보 등록
    @PostMapping("/api/attend")
    public ResponseEntity<AttendInfo> createAttend(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(value = "schedule_id") Long schedule_id,
            @RequestParam(value = "student_id") Long student_id
    ){
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) { // 학원만 생성 가능
            return ResponseEntity.ok().body(attendService.createSingleAttend(user, schedule_id, student_id));
        } else return ResponseEntity.ok().build();
    }

    //////////////////////////////////////////////////////////
    ///               업데이트(출석 정보 입력)                  //
    //////////////////////////////////////////////////////////
    // Map(Long, Byte) 을 입력받아 해당 Long 에 맞는 attend 개체의 attendance 값을 Byte 값으로 변경
    @PutMapping("/api/attend")
    public ResponseEntity<String> updateAttend(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody UpdateAttendListRequest request){
        return ResponseEntity.ok().body(attendService.updateAttends(user, request));
    }


    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////
    @PutMapping("/api/delete/attend")
    public ResponseEntity<String> deleteAttend(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(value = "attend_id") Long attend_id
    ) {
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) { // 학원만 삭제 가능
            return ResponseEntity.ok().body(attendService.deleteSingleAttend(user, attend_id));
        } else return ResponseEntity.ok().body("학원 계정만 접근이 가능합니다.");
    }
}
