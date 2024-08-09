package com.TeachingManager.TeachingManager.DTO.Teacher;

import com.TeachingManager.TeachingManager.domain.Teacher;
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

    public TeacherInfo(Teacher teacher) {
        this.teacher_id = teacher.getPk();
        this.teacher_name = teacher.getTeacher_name();
        this.age = teacher.getAge();
        this.birth = teacher.getBirth();
        this.phoneNum = teacher.getPhoneNum();
        this.gender = teacher.getGender();
        this.bank_account = teacher.getBank_account();
        this.salary = teacher.getSalary();
        this.nickname = teacher.getNickname();
        this.provider = teacher.getProvider();
        this.institute_id = teacher.getInstitutePk();

    }
}
