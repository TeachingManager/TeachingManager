package com.TeachingManager.TeachingManager.DTO.Schedule;

import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class  AddScheduleRequest {

    private String title;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String memo;

    public Schedule toEntity(Institute institute) {
        return Schedule.builder()
                .title(title)
                .start_date(start_date)
                .end_date(end_date)
                .memo(memo)
                .institute(institute)
                .build();
    }

    /// 아래는 세션 방식이라 주석처리
//    // 현재 접속자의 pk 값 가져오는 함수
//    public Institute getCurrentInstitute() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            System.out.println("principal = " + principal);
//            if (principal instanceof Institute current_institute) {
//                return current_institute;
//            }
//        }
//        return null;
//    }

}
