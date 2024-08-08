package com.TeachingManager.TeachingManager.Service.User.Teacher;

import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherDetailServiceImpl implements UserDetailsService {
    public final TeacherRepository teacherRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("teahcerDetailService 의 load 까지");
        Teacher teacher =  teacherRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
        return teacher;
    }
    public CustomUser loadUserByPk(Long pk){
        Teacher teacher =  teacherRepo.findByPk(pk).orElseThrow(() -> new IllegalArgumentException());
        return teacher;
    }
}

