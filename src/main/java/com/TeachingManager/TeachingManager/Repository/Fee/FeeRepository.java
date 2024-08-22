package com.TeachingManager.TeachingManager.Repository.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.domain.Fee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeeRepository {

    // 1년 짜리 가져오기
    List<EnrollYearFeeResponse> findYearFee(UUID institute_id, Short Year, Short Month);

    // 요금 테이블 가져오기
    Optional<Fee> findByInstituteDate(UUID institute_id, Short Year, Short Month);

    // 새 튜플 생성하기
    Fee save(Fee fee);

    long addMonthTotalAndPaidFee(UUID institute_id, Short Year, Short Month, long feeValue, long paidFeeValue);

    long declineMonthTotalAndPaidFee(UUID institute_id,  Short Year, Short Month, long feeValue, long paidFeeValue);

}