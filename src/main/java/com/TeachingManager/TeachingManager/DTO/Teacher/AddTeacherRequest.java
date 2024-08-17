package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class AddTeacherRequest {
    public String teacherName;
    public Byte age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate birth;
    public String phoneNum;
    public Character gender;
    public String email;
    public String bank_account;
    public String nickname;
    private String password;

}
