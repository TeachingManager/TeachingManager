package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Institute extends CustomUser {

    @Column(name = "institute_name", nullable = false)
    private String institute_name;

    @Column(name = "address", nullable = true, unique = false)
    private String address;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    public Institute(String email, String password, Long pk){
        this.setEmail(email);
        this.setPassword(password);
        this.setAuthorities(Role.PRESIDENT);
        this.setPk(pk);
        this.institute_name = "학원";
        this.phoneNum = "";
    }

    public Institute(Long pk){
        this.setEmail("");
        this.setPassword("");
        this.setAuthorities(Role.PRESIDENT);
        this.setPk(pk);
        this.institute_name = "학원";
        this.phoneNum = "";
    }

    @Builder
    public Institute(String email, String password, String auth, String institute_name, String address, String phoneNum){
        this.setEmail(email);
        this.setPassword(password);
        this.setAuthorities(Role.PRESIDENT);
        this.institute_name = institute_name;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public void update( String institute_name, String address, String phoneNum){
        this.institute_name = institute_name;
        this.phoneNum = phoneNum;
        this.address = address;
    }

}
