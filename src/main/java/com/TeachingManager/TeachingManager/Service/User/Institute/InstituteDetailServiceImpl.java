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

        System.out.println("찾은 institute의 비밀번호 = " + institute.getPassword());
        // 다른 민감한 정보들이 넘어가게 하지 않기 위해 User를 커스텀한 InstituteAdapter
        return institute;
    }
}
