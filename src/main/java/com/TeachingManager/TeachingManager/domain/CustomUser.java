package com.TeachingManager.TeachingManager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class CustomUser implements UserDetails {

    // 학원과 강사 모두가 사용할 속성들
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "pk", nullable = false, updatable = false)
    private UUID pk;

    @Column(name = "email",updatable = false, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "failedCount", nullable = false)
    private Byte failedCount  = 0;

    @Column(name="accountNonLocked", nullable = false)
    private Boolean accountNonLocked = true;

    @Column(name="enabled", nullable = false)
    private Boolean enabled = false;

    @Column(name = "authorities", nullable = false)
    private String authorities;

    public CustomUser(UUID pk, String email, String password, List<String> roles) {
        this.pk = pk;
        this.email = email;
        this.password = password;
        this.failedCount = 0;
        this.authorities = String.join(", ", roles);
    }

    //Role.java 에서 가져오는 것.
    public void setAuthorities(Role role) {
        this.authorities = role.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authorities.split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
