package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private final InstituteRepository instRepo;

    @Autowired
    private final TeacherRepository teacherRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Institute> institute = instRepo.findByEmail(email);
        if (institute.isPresent()) {
            return institute.get();
        }
        return teacherRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        // 다른 민감한 정보들이 넘어가게 하지 않기 위해 User를 커스텀한 InstituteAdapter 사용하는게 나을지도?
        // 또는 이러한 두번의 서치 과정을 하지 않기 위해 Custom 적용 방법을 추후에 고려
    }
}
