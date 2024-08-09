package com.TeachingManager.TeachingManager.Service.User.Teacher;

import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleInfo;
import com.TeachingManager.TeachingManager.DTO.Teacher.*;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.Schedule;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl {
    private final TeacherRepository teacherRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(AddTeacherRequest dto){
        return teacherRepo
                .save(Teacher.builder()
                        .email(dto.getEmail())
                        .nickname(dto.getNickname())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .teacher_name(dto.getTeacherName())
                        .birth(dto.getBirth())
                        .phoneNum(dto.getPhoneNum())
                        .age(dto.getAge())
                        .gender(dto.getGender())
                        .bank_account(dto.getBank_account())
                        .provider("Local")
                        .build()
                ).getPk();
    }

    public Long social_register(AddSocialTeacherRequest dto) {
        return teacherRepo
                .save(Teacher.builder()
                        .teacher_name(dto.getTeacherName())
                        .nickname(dto.getNickname())
                        .provider(dto.getProvider())
                        .age(dto.getAge())
                        .phoneNum(dto.getPhoneNum())
                        .age(dto.getAge())
                        .gender(dto.getGender())
                        .bank_account(dto.getBank_account())
                        .build()
                ).getPk();
    }

    public TeacherInfo search_teacher(Long teacher_id, Long institute_id) {
        Optional<Teacher> teacher = teacherRepo.findByPk(teacher_id);
        // 해당 티처가 존재하고, 요청 받은 학원 소속일 경우 정보 전달.
        if (teacher.isPresent() && Objects.equals(teacher.get().getInstitutePk(), institute_id)){
            System.out.println("teacher = " + teacher);
            Teacher teacherInstance = teacher.get();
            return convertToDTO(teacherInstance);
        }
        System.out.println("알맞은 teacher 없었음");
        // 알맞은 선생님이 없다면 빈 Info 전달
        return new TeacherInfo();
    }

    // 선생님 명단 조회
    public FindAllTeacherResponse search_allTeacher(Long institute_id) {
        List<Teacher> teacherList = teacherRepo.findByInstitute_Pk(institute_id);
        Set<TeacherInfo> teacherInfoList = teacherList.stream().map(this::convertToDTO).collect(Collectors.toSet());
        return new FindAllTeacherResponse(teacherInfoList);
    }

    public TeacherInfo convertToDTO(Teacher teacherInstance) {
        return new TeacherInfo(teacherInstance.getPk(), teacherInstance.getTeacher_name(),
                teacherInstance.getAge(), teacherInstance.getBirth(), teacherInstance.getPhoneNum(), teacherInstance.getGender(), teacherInstance.getBank_account(), teacherInstance.getSalary(), teacherInstance.getNickname(), teacherInstance.getProvider(), teacherInstance.getInstitutePk());
    }

}
