package com.TeachingManager.TeachingManager.Service.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.domain.CustomUser;

import java.util.List;

public interface FeeService {
    // 특정 달의 요금 정보를 가져오는 API
    List<EnrollFeeResponse> findMonthlyLectureFee(CustomUser user, Short year, Short month);

    // 특정 달로 부터 1년간의 요금정보를 가져오는 API
    List<EnrollYearFeeResponse> findYearLectureFee(CustomUser user, Short year, Short month);

    // 납부 내역 수정
    Boolean purchaseLectureFee(CustomUser user, Long enroll_id, int paid_amount ,Short year, Short month);

}
