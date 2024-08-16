package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        // 먼저 Institute를 찾는다
        Institute institute = instRepo.findByEmail(email).orElse(null);

        if (institute != null) {
            return institute; // Institute를 반환
        }

        // Institute가 없으면 Teacher를 찾는다
        return teacherRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)); // 혹은 teacher.getInstitute() 등 필요한 데이터 처리
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveStatus(CustomUser user) {
        if (user instanceof Institute) {
            System.out.println("학원 비번 틀림! 저장위함!  : " + user.getFailedCount());
            instRepo.save((Institute) user);
            System.out.println(" 저장 끝!");
        } else if (user instanceof Teacher) {
            System.out.println("강사 비번 틀림! 저장위함!  : " + user.getFailedCount());
            teacherRepo.save((Teacher) user);
            System.out.println(" 저장 끝!");
        }
    }
}
