package com.TeachingManager.TeachingManager.Service.student;

import com.TeachingManager.TeachingManager.Repository.student.StudentRepository;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.dto.AddStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    //학생 추가 메서드
    public Student save(AddStudentRequest request){

        return studentRepository.save(request.toEntity());
    }
}
