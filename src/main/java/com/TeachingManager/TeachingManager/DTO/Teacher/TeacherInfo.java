package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherInfo {
    private Long teacher_id;
    private String teacher_name;
    private Byte age;
    private LocalDate birth;
    private String phoneNum;
    private Character gender;
    private String bank_account;
    private Long salary;
    private String nickname ;
    private String provider;
    private Long institute_id;
}
