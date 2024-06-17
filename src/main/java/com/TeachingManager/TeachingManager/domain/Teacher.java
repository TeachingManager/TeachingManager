package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", updatable = false)
    private Long teacher_id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "teacher_name", nullable = false)
    private String teacher_name;

    @Column(name = "age", nullable = false)
    private Byte age;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    @Column(name = "gender", nullable = false)
    private Character gender;

    @Column(name = "bank_account", nullable = true)
    private String bank_account;

    @Column(name = "salary", nullable = true)
    private Long salary;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;



    @Builder
    public Teacher(String email,String password, String auth, String teacher_name, Byte age, Date birth, String phoneNum, Character gender, String bank_account, Long salary, Institute institute) {
        this.email = email;
        this.password = password;
        this.teacher_name = teacher_name;
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.bank_account = bank_account;
        this.salary = salary;
        this.institute = institute;
    }

    public void update(String teacher_name, Byte age, Date birth, String phoneNum, Character gender, String bank_account, Long salary) {
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
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
