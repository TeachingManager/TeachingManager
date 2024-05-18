package com.TeachingManager.TeachingManager.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;


    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL)
    private Set<Teacher> teachers;

    @Builder
    public Institute(String email, String institute_name, String address, String phoneNum){
        this.email = email;
        this.institute_name = institute_name;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public void update( String institute_name, String address, String phoneNum){
        this.institute_name = institute_name;
        this.address = address;
        this.phoneNum = phoneNum;
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
