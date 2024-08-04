package com.TeachingManager.TeachingManager.Service.User.Teacher;

import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
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
        Teacher teacher =  teacherRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
        return org.springframework.security.core.userdetails.User.builder()
                .username(teacher.getEmail()) // email 속성을 사용하여 사용자를 식별
                .password(teacher.getPassword()) // 비밀번호 사용
                .accountLocked(false) // 잠금 아닌 상태로
                .build();
    }
}
