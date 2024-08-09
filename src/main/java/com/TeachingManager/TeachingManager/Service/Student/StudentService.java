package com.TeachingManager.TeachingManager.Service.Student;

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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepo;

    //학생 추가 메서드
    public Student create_student(AddStudentRequest request, Long pk){
        Optional<Institute> institute = instituteRepo.findByPk(pk);
        if (institute.isPresent()){
            return studentRepository.save(request.toEntity(institute.get()));
        }
        else {
            System.out.println("존재하지 않는 학원에서의 요청입니다.");
            return null;
        }
    }

    //모든 학생 조회 메서드
    public List<Student> findAll(CustomUser user){
        if(user instanceof Teacher) {
            throw new RuntimeException("선생님은 권한이 없습니다.");
        }
        else{
            return studentRepository.findByInstitute_Pk(user.getPk());
        }
    }

    
    // 단일학생 조회 메서드
    public Student findById(CustomUser user, long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        if(student.getInstitute().getPk().equals(user.getPk())){
            return student;
        }
        else{
            throw new RuntimeException("올바르지 않은 접근입니다.");
        }
    }

    // 학생 삭제 메서드
    @Transactional
    public String delete(CustomUser user, long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        if(student.getInstitute().getPk().equals(user.getPk())){
            studentRepository.deleteById(id);
            return student.getName();
        } // 학생의 소속학원에서 온 요청이 아닐 경우
        else{
            throw new RuntimeException("올바르지 않은 접근입니다.");
        }
    }

    @Transactional
    public Student update(CustomUser user, long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        if(student.getInstitute().getPk().equals(user.getPk())){
            student.update(request.getName(), request.getAge(), request.getGrade(), request.getPhoneNumber(), request.getParentName(), request.getParentNumber(), request.getGender(), request.getLevel());
            return student;
        } // 학생의 소속
       else{
            throw new RuntimeException("올바르지 않은 접근입니다.");
        }
    }
}
