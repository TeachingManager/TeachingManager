package com.TeachingManager.TeachingManager.Service.User.Teacher;

import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherDetailServiceImpl implements UserDetailsService {
    public final TeacherRepository teacherRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Teacher teacher =  teacherRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
        return teacher;
    }

    public Teacher loadTeacherByUsername(String email) throws UsernameNotFoundException {
        return teacherRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
    }
    public CustomUser loadUserByPk(UUID pk){
        Teacher teacher =  teacherRepo.findByPk(pk).orElseThrow(() -> new IllegalArgumentException());
        return teacher;
    }
}

