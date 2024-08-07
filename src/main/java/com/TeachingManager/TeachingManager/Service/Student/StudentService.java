package com.TeachingManager.TeachingManager.Service.Student;

import com.TeachingManager.TeachingManager.Repository.Student.StudentRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.DTO.Student.AddStudentRequest;
import com.TeachingManager.TeachingManager.DTO.Student.UpdateStudentRequest;
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
    public Student save(AddStudentRequest request, String email){
        Optional<Institute> institute = instituteRepo.findByEmail(email);
        if (institute.isPresent()){
            return studentRepository.save(request.toEntity(institute.get()));
        }
        else {
            System.out.println("존재하지 않는 학원입니다.");
            return null;
        }
    }

    //모든 학생 조회 메서드
    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public Student findById(long id){
        return studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id){
        studentRepository.deleteById(id);
    }

    @Transactional
    public Student update(long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        student.update(request.getName(), request.getAge(), request.getGrade(), request.getPhoneNumber(), request.getParentName(), request.getParentNumber(), request.getGender(), request.getLevel());

        return student;
    }


}
