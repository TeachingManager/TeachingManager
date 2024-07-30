package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Institute implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institute_id", updatable = false)
    private Long institute_id;

    @Column(name = "email",updatable = false, nullable = false, unique = true)
    private String email;

    @Column(name = "institute_name", nullable = false)
    private String institute_name;

    @Column(name = "address", nullable = true, unique = false)
    private String address;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "password", nullable = false)
    private String password;


//    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL)
//    private Set<Teacher> teachers;

//    @OneToMany(mappedBy = "institute")
//    private List<Lecture> lectures = new ArrayList<>();

    @Builder
    public Institute(String email, String password, String auth, String institute_name, String address, String phoneNum){
        this.email = email;
        this.password = password;
        this.institute_name = institute_name;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public void update( String institute_name, String address, String phoneNum){
        this.institute_name = institute_name;
        this.phoneNum = phoneNum;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((new SimpleGrantedAuthority("user")));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
