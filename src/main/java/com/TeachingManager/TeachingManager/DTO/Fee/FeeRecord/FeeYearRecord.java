package com.TeachingManager.TeachingManager.DTO.Fee.FeeRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeeYearRecord {
    private Short Year;
    private Short Month;
    private long totalMonthFee = 0;
    private long paidMonthFee = 0;
}
