package com.TeachingManager.TeachingManager.DTO.Teacher;

import com.TeachingManager.TeachingManager.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTeacherRequest {
    private String teacher_name;
    private Byte age;
    private LocalDate birth;
    private String phoneNum;
    private Character gender;
    private String bank_account;
    private Long salary;
    private String nickname ;
}
