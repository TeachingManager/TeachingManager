package com.TeachingManager.TeachingManager.Repository.Student;

import com.TeachingManager.TeachingManager.domain.Student;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Component
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository{

    private final EntityManager em;

    @Override
    public List<Student> findByInstitute_Pk(UUID pk) {
        return em.createQuery("SELECT st " +
                "FROM Student st " +
                "WHERE st.institute.pk = :instituteId", Student.class)
                .setParameter("instituteId", pk)
                .getResultList();
    }

    @Override
    public List<Student>  findByInstitute_and_teacher_Pk(UUID instituteId, UUID teacherId, Short year, Short month) {
        return em.createQuery(
                        "SELECT st " +
                                "FROM Student st " +
                                "WHERE st.institute.pk = :instituteId " +
                                "AND EXISTS ( " +
                                "    SELECT 1 " +
                                "    FROM Enroll en " +
                                "    JOIN en.lecture lec " +
                                "    JOIN lec.teacher tech " +
                                "    WHERE en.student = st " +
                                "    AND tech.pk = :teacherId " +
                                "    AND en.year = :year " +
                                "    AND en.month = :month " +
                                ") ", Student.class)
                .setParameter("instituteId", instituteId)
                .setParameter("teacherId", teacherId)
                .setParameter("year", year)
                .setParameter("month", month)
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
    public Optional<Student> findById(UUID institute_id, Long student_id) {
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
    public String deleteById(UUID institute_id, Long student_id) {
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
