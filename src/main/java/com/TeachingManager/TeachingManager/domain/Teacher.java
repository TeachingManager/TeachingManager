package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", updatable = false)
    private Long teacher_id;

    @Column(name = "teacher_name", nullable = false)
    private String teacher_name;

    @Column(name = "age", nullable = false)
    private Byte age;

    @Column(name = "birth", nullable = false)
    private LocalDateTime birth;

    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    @Column(name = "gender", nullable = false)
    private Character gender;

    @Column(name = "bank_account", nullable = false)
    private String bank_account;

    @Column(name = "salary", nullable = false)
    private Long salary;

    @Builder
    public Teacher(String teacher_name, Byte age, LocalDateTime birth, String phoneNum, Character gender, String bank_account, Long salary) {
        this.teacher_name = teacher_name;
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.bank_account = bank_account;
        this.salary = salary;
    }

    public void update(String teacher_name, Byte age, LocalDateTime birth, String phoneNum, Character gender, String bank_account, Long salary) {
        this.teacher_name = teacher_name;
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.bank_account = bank_account;
        this.salary = salary;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
