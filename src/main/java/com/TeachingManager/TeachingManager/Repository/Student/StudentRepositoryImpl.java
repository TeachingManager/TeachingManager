package com.TeachingManager.TeachingManager.Repository.Student;

import com.TeachingManager.TeachingManager.domain.Student;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository{

    private final EntityManager em;

    @Override
    public List<Student> findByInstitute_Pk(Long pk) {
        return em.createQuery("SELECT st " +
                "FROM Student st " +
                "WHERE st.institute.pk = :instituteId", Student.class)
                .setParameter("instituteId", pk)
                .getResultList();
    }

    // 새 학생 저장하는 메서드
    @Override
    @Transactional
    public Student save(Student student) {
        em.persist(student);
        return student;
    }

    @Override
    public Optional<Student> findById(Long institute_id, Long student_id) {
        return em.createQuery("SELECT st " +
                                "FROM Student st " +
                                "WHERE st.institute.pk = :instituteId " +
                                "AND st.id = :studentId"
                        , Student.class)
                .setParameter("instituteId", institute_id)
                .setParameter("studentId", student_id)
                .getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public String deleteById(Long institute_id, Long student_id) {
        int deleteCount = em.createQuery("DELETE FROM Student st " +
                        "WHERE st.institute.pk = :instituteId  " +
                        "AND st.id = :studentId")
                .setParameter("instituteId", institute_id)
                .setParameter("studentId", student_id)
                .executeUpdate();;

        if(deleteCount == 0) {
            return "알맞는 대상이 없었음";
        }
        return "삭제완료";
    }
}
