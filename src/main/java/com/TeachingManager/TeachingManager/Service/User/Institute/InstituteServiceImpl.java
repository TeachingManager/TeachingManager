package com.TeachingManager.TeachingManager.Service.User.Institute;

import com.TeachingManager.TeachingManager.DTO.Institute.AddInstituteRequest;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstituteServiceImpl {
    private final InstituteRepository instRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(AddInstituteRequest dto){
        Institute temp = Institute.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .institute_name(dto.getInsName())
                .address(dto.getAddress())
                .phoneNum(dto.getPhoneNum()).build();

        return instRepo
        .save(temp
        ).getPk();
    }

    //현재 접속중인
}
