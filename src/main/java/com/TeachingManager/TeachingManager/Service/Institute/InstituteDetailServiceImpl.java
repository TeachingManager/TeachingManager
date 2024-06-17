package com.TeachingManager.TeachingManager.Service.Institute;

import com.TeachingManager.TeachingManager.Repository.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InstituteDetailServiceImpl implements UserDetailsService {
    @Autowired
    private final InstituteRepository instRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Institute institute = instRepo.searchByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));

        // 다른 민감한 정보들이 넘어가게 하지 않기 위해
        return org.springframework.security.core.userdetails.User.builder()
                .username(institute.getEmail()) // email 속성을 사용하여 사용자를 식별
                .password(institute.getPassword()) // 비밀번호 사용
                .build();
    }
}
