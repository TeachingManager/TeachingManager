package com.TeachingManager.TeachingManager.Service.Institute;

import com.TeachingManager.TeachingManager.Repository.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.InstituteAdapter;
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
        Institute institute = instRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));

        // 다른 민감한 정보들이 넘어가게 하지 않기 위해 User를 커스텀한 InstituteAdapter
        return institute;
    }
}