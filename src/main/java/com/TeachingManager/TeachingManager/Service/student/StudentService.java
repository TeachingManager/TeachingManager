package com.TeachingManager.TeachingManager.Service.student;

import com.TeachingManager.TeachingManager.Repository.student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.dto.AddStudentRequest;
import com.TeachingManager.TeachingManager.dto.UpdateStudentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    //학생 추가 메서드
    public Student save(AddStudentRequest request){

        return studentRepository.save(request.toEntity());
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
