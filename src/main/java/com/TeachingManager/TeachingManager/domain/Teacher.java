package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends CustomUser {

    @Column(name = "teacher_name", nullable = false)
    private String teacher_name;

    @Column(name = "age", nullable = false)
    private Byte age;

    @Column(name = "birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    @Column(name = "gender", nullable = false)
    private Character gender;

    @Column(name = "bank_account", nullable = true)
    private String bank_account;

    @Column(name = "salary", nullable = true)
    private Long salary;

    @Column(name = "nickname", nullable = false)
    private String nickname = "닉네임";

    // OAuth2.0 제공자 (구글, 네이버, 카카오)
    @Column(name = "provider", nullable = true)
    private String provider;


    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    // 외래키 가져오는 함수
    public Long getInstitutePk() {
        return institute != null ? institute.getPk() : null; 
    }
    
    public Teacher(String email, String password, Long pk, Long inst_id){
        this.setEmail(email);
        this.setPassword(password);
        this.setAuthorities(Role.PRESIDENT);
        this.setPk(pk);
        this.nickname = "";
        this.teacher_name = "";
        this.age = 0;
        this.birth = LocalDate.parse("2024-08-09");
        this.phoneNum = "";
        this.gender = '?';
        this.bank_account = bank_account;
        this.salary = 0L;
        this.provider = "";
        this.institute = new Institute(inst_id);
    }
    
    @Builder
    public Teacher(String email, String nickname, String password, String auth, String teacher_name, Byte age, LocalDate birth, String phoneNum, Character gender, String bank_account, Long salary, Institute institute, String provider
    ) {
        this.setEmail(email);
        this.setPassword(password);
        this.setAuthorities(Role.TEACHER);
        this.nickname = nickname;
        this.teacher_name = teacher_name;
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.bank_account = bank_account;
        this.salary = salary;
        this.provider = provider;
        this.institute = institute;
    }

    public void update(String teacher_name, Byte age, LocalDate birth, String phoneNum, Character gender, String bank_account, Long salary, String nickname) {
        this.teacher_name = teacher_name;
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.bank_account = bank_account;
        this.salary = salary;
        this.nickname = nickname;
    }
}
