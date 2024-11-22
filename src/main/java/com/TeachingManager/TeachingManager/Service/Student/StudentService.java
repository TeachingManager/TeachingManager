package com.TeachingManager.TeachingManager.Service.Student;

import com.TeachingManager.TeachingManager.DTO.Student.StudentResponse;
import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.DTO.Student.AddStudentRequest;
import com.TeachingManager.TeachingManager.DTO.Student.UpdateStudentRequest;
import com.TeachingManager.TeachingManager.domain.Teacher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepo;

    //학생 추가 메서드
    public Student create_student(AddStudentRequest request, CustomUser user){
        Optional<Institute> institute = instituteRepo.findByPk(user.getPk());
        if (institute.isPresent()){
            return studentRepository.save(request.toEntity(institute.get()));
        }
        else {
            System.out.println("존재하지 않는 학원에서의 요청입니다.");
            return null;
        }
    }

    //모든 학생 조회 메서드
    public List<StudentResponse>findAll(CustomUser user){
        // 선생님일 경우 자기가 가르치는
        if(user instanceof Teacher) {
            LocalDate today = LocalDate.now();
            Short Year = (short) today.getYear();
            Short Month = (short) today.getMonthValue();
            return studentRepository
                    .findByInstitute_and_teacher_Pk(
                            ((Teacher) user).getInstitutePk(),
                            user.getPk(),
                            Year,
                            Month)
                    .stream()
                    .map(StudentResponse::new)
                    .toList();
        }
        else{
            return studentRepository.findByInstitute_Pk(user.getPk()) .stream()
                    .map(StudentResponse::new)
                    .toList();
        }
    }

    
    // 단일학생 조회 메서드
    public Student findById(CustomUser user, long id){
        if(user instanceof Teacher) {
            return studentRepository.findById(((Teacher) user).getInstitutePk(), id).orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        }
        else {
            return studentRepository.findById(user.getPk(), id).orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        }
    }

    // 학생 삭제 메서드
    @Transactional
    public String delete(CustomUser user, long id){
        Student student = studentRepository.findById(user.getPk(), id).orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        studentRepository.deleteById(user.getPk(), id);
        return student.getName();
    }

    @Transactional
    public Student update(CustomUser user, long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(user.getPk(), id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        student.update(request.getName(), request.getAge(), request.getGrade(), request.getPhoneNumber(), request.getParentName(), request.getParentNumber(), request.getGender(), request.getLevel());
        studentRepository.save(student);
        return student;
    }
}
