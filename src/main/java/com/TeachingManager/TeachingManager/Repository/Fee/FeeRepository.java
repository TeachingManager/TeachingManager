package com.TeachingManager.TeachingManager.Repository.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.domain.Fee;

import java.util.List;
import java.util.Optional;

public interface FeeRepository {

    // 1년 짜리 가져오기
    List<EnrollYearFeeResponse> findYearFee(Long institute_id, Short Year, Short Month);

    // 요금 테이블 가져오기
    Optional<Fee> findByInstituteDate(Long institute_id, Short Year, Short Month);

    // 새 튜플 생성하기
    Fee save(Fee fee);

    long addMonthTotalFee(Long institute_id, Short Year, Short Month, long feeValue);

    long declineMonthTotalAndPaidFee(Long institute_id,  Short Year, Short Month, long feeValue);

}
