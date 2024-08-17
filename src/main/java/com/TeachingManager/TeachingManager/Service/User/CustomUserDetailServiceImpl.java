package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.config.exceptions.UserDoesNotExistException;
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
import java.util.UUID;


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
                .orElseThrow(() -> new UserDoesNotExistException(email));
    }

    public CustomUser loadCustomUserByPk(UUID pk) {
        System.out.println("loadUserByUsername");
        // 먼저 Institute를 찾는다
        Institute institute = instRepo.findByPk(pk).orElse(null);

        if (institute != null) {
            return institute; // Institute를 반환
        }
        // Institute가 없으면 Teacher를 찾는다
        return teacherRepo.findByPk(pk)
                .orElseThrow(() -> new UserDoesNotExistException("없음."));
    }



//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveStatus(CustomUser user) {
        if (user instanceof Institute) {
            System.out.println("학원 비번 틀림! 저장위함!  : " + user.getFailedCount());
            instRepo.save((Institute) user);

        } else if (user instanceof Teacher) {
            System.out.println("강사 비번 틀림! 저장위함!  : " + user.getFailedCount());
            teacherRepo.save((Teacher) user);

        }
    }
}
