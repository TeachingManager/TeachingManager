package com.TeachingManager.TeachingManager.Repository.Schedule;

import com.TeachingManager.TeachingManager.domain.Schedule;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Transactional
@Component
public class JpaScheduleRepository  implements ScheduleRepository{

    private final EntityManager em;

    public JpaScheduleRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Schedule save(Schedule sc) {
        em.persist(sc);
        return sc;
    }

    @Override
    public void delete(Long scid) {
        Schedule sc = em.find(Schedule.class, scid);
        if (sc != null) {
            em.remove(sc);
        }
    }

    @Override
    public Optional<Schedule> searchById(Long scid) {
        Schedule sc =  em.find(Schedule.class, scid); //pk 라 가능
        return Optional.ofNullable(sc);
    }

    @Override
    public Collection<Schedule> search_all(Long institute_id) {
        return em.createQuery("select sc from Schedule sc", Schedule.class)
                .getResultList();
    }

    @Override
    public Optional<Schedule> filter_by_date(Long institute_id, Date date_info) {
        return Optional.empty();
    }
}
