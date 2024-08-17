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
}
