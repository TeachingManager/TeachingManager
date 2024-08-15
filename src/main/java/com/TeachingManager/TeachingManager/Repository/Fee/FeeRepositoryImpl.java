package com.TeachingManager.TeachingManager.Repository.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.domain.Enroll;
import com.TeachingManager.TeachingManager.domain.Fee;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class FeeRepositoryImpl implements FeeRepository{
    private final EntityManager em;

    @Override
    public List<EnrollYearFeeResponse> findYearFee(Long institute_id, Short year, Short month) {

        return em.createQuery(
                    "SELECT new com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse(" +
                                " :currentYear," +
                                " :currentMonth," +
                                " COALESCE(fee.totalMonthFee, 0), " + // 없으면 0 반환.
                                " COALESCE(fee.payedMonthFee, 0)) " + // 없으면 0 반환.
                        "FROM Fee fee " +
                        "WHERE fee.institute.pk = :instituteId " +
                                "AND ((fee.year = :currentYear AND fee.month <= :currentMonth) " +
                                "OR (fee.year = :currentYear - 1 AND fee.month > :currentMonth))"
                        , EnrollYearFeeResponse.class)
                .setParameter("instituteId", institute_id)
                .setParameter("currentYear", year)
                .setParameter("currentMonth", month).getResultList();
    }

    @Override
    public Optional<Fee> findByInstituteDate(Long institute_id, Short year, Short month) {
        return em.createQuery(
                        "SELECT fee " + // 없으면 0 반환.
                                "FROM Fee fee " +
                                "WHERE fee.institute.pk = :instituteId " +
                                "AND fee.year = :currentYear " +
                                "AND fee.month = :currentMonth"
                        , Fee.class)
                .setParameter("instituteId", institute_id)
                .setParameter("currentYear", year)
                .setParameter("currentMonth", month).getResultList().stream().findFirst();
    }

    @Override
    public Fee save(Fee fee) {
        em.persist(fee);
        return fee;
    }


    @Override
    public long addMonthTotalAndPaidFee(Long institute_id, Short year, Short month, long feeValue, long paidFeeValue) {
        return em.createQuery("UPDATE Fee f " +
                        "SET f.totalMonthFee = f.totalMonthFee + :feeValue, f.payedMonthFee = f.payedMonthFee + :paidFeeValue " +
                        "WHERE f.institute.pk =: instituteId " +
                        "AND f.year = :year " +
                        "AND f.month = :month")
                .setParameter("instituteId", institute_id)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("feeValue", feeValue)
                .executeUpdate();
    }

    @Override
    public long declineMonthTotalAndPaidFee(Long institute_id, Short year, Short month, long feeValue, long paidFeeValue) {
        return em.createQuery("UPDATE Fee f " +
                        "SET f.totalMonthFee = f.totalMonthFee - :feeValue, f.payedMonthFee = f.payedMonthFee - :paidFeeValue " +
                        "WHERE f.institute.pk =: instituteId " +
                        "AND f.year = :year " +
                        "AND f.month = :month")
                .setParameter("instituteId", institute_id)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("feeValue", feeValue)
                .setParameter("paidFeeValue", paidFeeValue)
                .executeUpdate();
    }
}
