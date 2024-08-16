package com.TeachingManager.TeachingManager.Service.Lecture;

import com.TeachingManager.TeachingManager.DTO.Lecture.AddLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Lecture.UpdateLectureRequest;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.Repository.Lecture.LectureRepository;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService{

    private final LectureRepository lectureRepository;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public Lecture registerLecture(AddLectureRequest request, CustomUser user) {
        Optional<Institute> institute = instituteRepository.findById(user.getPk());
        Optional<Teacher> teacher = teacherRepository.findById(request.getTeacherId());
        if (institute.isPresent() && teacher.isPresent()) {
            return lectureRepository.save(request.toEntity(institute.get(), teacher.get()));
        }
        else {
            return null;
        }
    }

    @Override
    public Lecture findLecture(CustomUser user, Long id) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            Optional<Lecture> lecture = lectureRepository.findOneById(user.getPk(), id);
            System.out.println("institute find lecture");
            if (lecture.isPresent()) {
                return lecture.get();
            }
            else {
                throw new RuntimeException("학원-강의 잘못된 접근");
            }
        }
        else {
            Optional<Lecture> lecture = lectureRepository.findOneById(((Teacher) user).getInstitute().getPk(), id);
            System.out.println("teacher find lecture");
            if (lecture.isPresent()) {
                return lecture.get();
            }
            else {
                throw new RuntimeException("선생-강의 잘못된 접근");
            }
        }
    }

    @Override
    public void updateLecture(UpdateLectureRequest request, CustomUser user, Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(request.getTeacherId());
        Optional<Lecture> lecture = lectureRepository.findOneById(user.getPk(), id);
        if (teacher.isPresent() && lecture.isPresent()) {
            lecture.get().update(request.getName(), request.getCategory(), request.getGrade(), request.getFee(), request.getTime(), teacher.get());
            lectureRepository.save(lecture.get());
        }
        else {
            throw new RuntimeException("잘못된 업데이트");
        }
    }

    @Override
    public void deleteLecture(CustomUser user, Long id) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            System.out.println("institute delete lecture");
            lectureRepository.delete(user.getPk(), id);
        }
        else {
            throw new RuntimeException("강사는 삭제 불가");
        }
    }

    @Override
    public List<Lecture> findAll(CustomUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            List<Lecture> lectures = lectureRepository.findAll(user.getPk());
            System.out.println("institute find all lecture");
            System.out.println(lectures.getFirst().getTeacher().getPk());
            if (lectures.isEmpty()) {
                throw new RuntimeException("학원-강의 잘못된 접근");
            }
            else {
                return lectures;
            }
        }
        else {
            List<Lecture> lectures = lectureRepository.findAll(((Teacher) user).getInstitute().getPk());
            System.out.println("teacher find all lecture");
            System.out.println(((Teacher) user).getInstitute().getPk());
            if (lectures.isEmpty()) {
                throw new RuntimeException("선생-강의 잘못된 접근");
            }
            else {
                return lectures;
            }
        }
    }
}
