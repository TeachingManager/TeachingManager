package com.TeachingManager.TeachingManager.Service.Teacher;

import com.TeachingManager.TeachingManager.Repository.Teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements UserDetailsService {
    public final TeacherRepository teacherRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return teacherRepo.searchByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
