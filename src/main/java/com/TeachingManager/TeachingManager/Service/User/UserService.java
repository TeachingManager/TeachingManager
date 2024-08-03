package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.UserRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepo;
//    public CustomUser findByPk(Long pk) {
//        return userRepo.findByPk(pk).orElseThrow(()->new IllegalArgumentException("Unexpected User"));
//    }
}
