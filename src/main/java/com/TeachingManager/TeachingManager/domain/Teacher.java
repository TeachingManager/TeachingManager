package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends CustomUser {

    @Column(name = "teacher_name", nullable = false)
    private String teacher_name;

    @Column(name = "age", nullable = true)
    private Byte age;

    @Column(name = "birth", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Column(name = "phoneNum", nullable = true)
    private String phoneNum;

    @Column(name = "gender", nullable = true)
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Institute institute;

    // 외래키 가져오는 함수
    public UUID getInstitutePk() {
        return institute != null ? institute.getPk() : (UUID) null;
    }
    
    public Teacher(String email, String password, UUID pk, UUID inst_id){
        this.setEmail(email);
        this.setPassword(password);
        this.setAuthorities(Role.TEACHER);
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

    // 소셜 로그인시 사용할 생성자
    public Teacher(String email, String nickname, String provider){
        this.setEmail(email);
        this.setPassword(setRandomString());
        this.setAuthorities(Role.TEACHER);
        this.nickname = nickname;
        this.teacher_name = nickname;
        this.age = 20;
        this.birth = LocalDate.now();
        this.phoneNum = "";
        this.gender = '?';
        this.bank_account = null;
        this.salary = 0L;
        this.provider = provider;
        this.institute = null;
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

    public void setTempPassword(){
        this.setPassword(setRandomString());
    }


    private String setRandomString() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom secureRandom = new SecureRandom();

        int passwordLength = 8 + secureRandom.nextInt(5); // 0에서 4까지의 랜덤 숫자를 더해 8~12 생성
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }
}
