package com.TeachingManager.TeachingManager.DTO.Teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddSocialTeacherRequest {
    public String teacherName;
    public Byte age;
    public Date birth;
    public String phoneNum;
    public Character gender;
    public String bank_account;
}
