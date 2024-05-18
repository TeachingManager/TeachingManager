package com.TeachingManager.TeachingManager.Service.Institute;

import com.TeachingManager.TeachingManager.Repository.Institute.InstituteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InstituteServiceImpl implements UserDetailsService {
    private final InstituteRepository instRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return instRepo.searchByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
    }
}
