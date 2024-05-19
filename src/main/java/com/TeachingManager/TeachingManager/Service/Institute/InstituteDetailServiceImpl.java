package com.TeachingManager.TeachingManager.Service.Institute;

import com.TeachingManager.TeachingManager.Repository.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InstituteDetailImpl implements UserDetailsService {
    private final InstituteRepository instRepo;

    @Override
    public Institute loadUserByUsername(String email) {
        return instRepo.searchByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
    }
}
