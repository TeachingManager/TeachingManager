package com.TeachingManager.TeachingManager.Service.User.Teacher;

import com.TeachingManager.TeachingManager.DTO.Teacher.AddSocialTeacherRequest;
import com.TeachingManager.TeachingManager.DTO.Teacher.AddTeacherRequest;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl {
    private final TeacherRepository teacherRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(AddTeacherRequest dto){
        return teacherRepo
                .save(Teacher.builder()
                        .email(dto.getEmail())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .teacher_name(dto.getTeacherName())
                        .age(dto.getAge())
                        .phoneNum(dto.getPhoneNum())
                        .age(dto.getAge())
                        .gender(dto.getGender())
                        .bank_account(dto.getBank_account())
                        .build()
                ).getPk();
    }

    public Long social_register(AddSocialTeacherRequest dto) {
        return teacherRepo
                .save(Teacher.builder()
                        .teacher_name(dto.getTeacherName())
                        .age(dto.getAge())
                        .phoneNum(dto.getPhoneNum())
                        .age(dto.getAge())
                        .gender(dto.getGender())
                        .bank_account(dto.getBank_account())
                        .build()
                ).getPk();
    }

}
