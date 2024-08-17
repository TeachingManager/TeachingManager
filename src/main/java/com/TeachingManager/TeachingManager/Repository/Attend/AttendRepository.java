package com.TeachingManager.TeachingManager.Repository.Attend;


import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.LectureMonthAttendanceRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentMonthLectureAttendRecord;
import com.TeachingManager.TeachingManager.DTO.Attend.RepoDto.StudentsMonthAttendRecord;
import com.TeachingManager.TeachingManager.domain.Attend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendRepository {

    Attend save(Attend attend);

    String delete(UUID institute_id, Long attend_id);

    String deleteMonthAttend(UUID institute_id, Long lecture_id, Long student_id, LocalDate date_info);

    Optional<Attend> searchById (UUID institute_id, Long attend_id);

    // 특정 스케줄의 모든 학생 출석 정보 가져오기
    List<Attend> searchAttendanceByScheduleId(UUID institute_id, Long schedule_id);

    // 특정 스케줄의 특정 학생 출결 불러오기.
    Optional<Attend> searchOneByStudentId(UUID institute_id, Long schedule_id, Long student_id);

    // 특정 학생의 특정 달의 출결 모두 불러오기
    List<StudentsMonthAttendRecord> searchMonthlyAttendanceByStudentId(UUID institute_id, Long student_id, LocalDate yearMonthDay);

    // 특정 강의의 특정 달의 출결 불러오기
    List<LectureMonthAttendanceRecord> searchMonthlyAttendanceByLectureId(UUID institute_id, Long lecture_id, LocalDate yearMonthDay);

    // 특정 학생의 특정 강의의 특정 달 출결 불러오기
    List<StudentMonthLectureAttendRecord> searchMonthlyAttendanceByLectureAndStudentId(UUID institute_id, Long lecture_id, Long student_id, LocalDate yearMonthDay);

}
