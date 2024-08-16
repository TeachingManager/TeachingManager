package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService{

    private final InstituteRepository instRepo;
    private final TeacherRepository teacherRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        System.out.println("loadUserByUsername");
        return instRepo.findByEmail(email)
                .map(institute -> (UserDetails) institute)
                .orElseGet(() -> teacherRepo.findByEmail(email)
                        .map(teacher -> (UserDetails) teacher)
                        .orElseThrow(() -> new UsernameNotFoundException(email)));
    }


    public CustomUser loadCustomUserByUsername(String email) {
        System.out.println("loadUserByUsername");
        return instRepo.findByEmail(email)
                .orElseGet(() -> teacherRepo.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(email)).getInstitute());
    }
}
