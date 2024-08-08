package com.TeachingManager.TeachingManager.Service.User.Institute;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InstituteDetailServiceImpl implements UserDetailsService {
    @Autowired
    private final InstituteRepository instRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Institute institute = instRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        // 다른 민감한 정보들이 넘어가게 하지 않기 위해 User를 커스텀한 InstituteAdapter
        return institute;
    }

    public Institute loadInstituteByUsername(String email) {
        return instRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public UserDetails loadUserByPk(Long pk){
        Institute institute =  instRepo.findByPk(pk).orElseThrow(() -> new IllegalArgumentException());
        return institute;
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(institute.getEmail()) // email 속성을 사용하여 사용자를 식별
//                .password(institute.getPassword()) // 비밀번호 사용
//                .build();
    }
}
