package com.TeachingManager.TeachingManager.Service.student;

import com.TeachingManager.TeachingManager.Repository.student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.dto.AddStudentRequest;
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
}
