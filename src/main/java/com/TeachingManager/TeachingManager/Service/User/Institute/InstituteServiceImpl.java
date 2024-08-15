package com.TeachingManager.TeachingManager.Service.User.Institute;

import com.TeachingManager.TeachingManager.DTO.Institute.AddInstituteRequest;
import com.TeachingManager.TeachingManager.DTO.Institute.UpdateInstituteRequest;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        return instRepo.save(temp).getPk();
    }

    // 학원 정보 업데이트
    @Transactional
    public Institute update_Institute(CustomUser user, UpdateInstituteRequest request) {
        if(user instanceof Teacher){
            throw new RuntimeException("학원이 아닌 유저가 접근");
        }
        else{
            Institute institute = instRepo.findByPk(user.getPk()).orElseThrow(() -> new IllegalArgumentException("not found : " + user.getPk()));
            institute.update(request.getInstitute_name(), request.getAddress(),request.getPhoneNum());
            instRepo.save(institute);
            return institute;
        }
    }


    // 학원 탈퇴
    @Transactional
    public String delete_Institute(CustomUser user){
        if(user instanceof Teacher){
            return "잘못된 학원 삭제 기능 접근";
        }
        else {
            Optional<Institute> institute = instRepo.findByEmail(user.getEmail());
            if(institute.isPresent()){
                instRepo.delete(institute.get());
                return "삭제 완료!";
            }
            else{
                return "존재하지 않는 학원 회원입니다.";
            }
        }
    }

}
