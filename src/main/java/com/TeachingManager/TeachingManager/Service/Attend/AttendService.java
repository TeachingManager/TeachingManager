package com.TeachingManager.TeachingManager.Service.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.AttendInfo;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthLectureAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.MonthStudentAttendResponse;
import com.TeachingManager.TeachingManager.DTO.Attend.Request.UpdateAttendListRequest;
import com.TeachingManager.TeachingManager.domain.CustomUser;

import java.time.LocalDate;
import java.util.List;

public interface AttendService {
    //////////////////////////////////////////////////////////
    ///                       추가                           //
    //////////////////////////////////////////////////////////

    // 주어진 스케줄 아이디, 학생 아이디로 하나의 출석 튜플을 만드는 서비스함수
    AttendInfo createSingleAttend(CustomUser user, Long schedule_id, Long student_id);


    //////////////////////////////////////////////////////////
    ///                       조회                           //
    //////////////////////////////////////////////////////////

    // 출석 아이디로 개별 조회하는 서비스 함수
    AttendInfo searchById(CustomUser user, Long attend_id);

   
    // 특정 달의 특정 강의의 출석  리스트
    List<MonthLectureAttendResponse> findMonthlyLectureAttendance(CustomUser user, Long lecture_id, LocalDate date_info);

    // 특정 달의 특정 학생의 출석  리스트
    List<MonthStudentAttendResponse>  findMonthlyStudentAttendance(CustomUser user, Long student_id, LocalDate date_info);

    
    //////////////////////////////////////////////////////////
    ///               업데이트(출석 정보 입력)                  //
    //////////////////////////////////////////////////////////

    // 전달된 값들 반영
    String updateAttends(CustomUser user, UpdateAttendListRequest request);


    //////////////////////////////////////////////////////////
    ///                       삭제                           //
    //////////////////////////////////////////////////////////

    // 단 한 개의 출석 삭제 서비스
    String deleteSingleAttend(CustomUser user, Long attend_id);
}
