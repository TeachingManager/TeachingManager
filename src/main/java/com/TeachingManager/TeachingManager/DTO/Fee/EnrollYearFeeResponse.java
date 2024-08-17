package com.TeachingManager.TeachingManager.DTO.Fee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollYearFeeResponse {
    private Short Year;
    private Short Month;
    private long totalMonthFee = 0;
    private long payedMonthFee = 0;

    // 추가 생성자 정의
    public EnrollYearFeeResponse(Integer year, Short month, Long totalMonthFee, Long payedMonthFee) {
        this.Year = year.shortValue(); // Integer를 Short로 변환
        this.Month = month;
        this.totalMonthFee = totalMonthFee;
        this.payedMonthFee = payedMonthFee;
    }
}
