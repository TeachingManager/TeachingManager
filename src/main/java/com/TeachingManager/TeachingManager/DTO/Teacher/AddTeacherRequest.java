package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddTeacherRequest {
    public String teacherName;
    public Byte age;
    public Date birth;
    public String phoneNum;
    public Character gender;
    public String email;
    public String bank_account;
    private String password;
}
