package com.TeachingManager.TeachingManager.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
public class InstituteAdapter extends User {
    private Institute institute;
    private Long pk;

    public InstituteAdapter(Institute institute) {
        super(institute.getUsername(), institute.getPassword(),institute.getAuthorities());
        this.institute = institute;
        this.pk = institute.getInstitute_id();
    }
}
