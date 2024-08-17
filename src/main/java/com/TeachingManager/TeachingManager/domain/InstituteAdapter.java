package com.TeachingManager.TeachingManager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Getter
public class InstituteAdapter extends User {
    private Institute institute;
    private UUID pk;
    private String institute_name;

    public InstituteAdapter(Institute institute) {
        super(institute.getUsername(), institute.getPassword(),institute.getAuthorities());
        this.institute = institute;
        this.pk = institute.getPk();
        this.institute_name = institute.getInstitute_name();
    }
}
