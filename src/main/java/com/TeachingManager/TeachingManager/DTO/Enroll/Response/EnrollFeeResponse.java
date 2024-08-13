package com.TeachingManager.TeachingManager.DTO.Enroll.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollFeeResponse {
    private long totalFee = 0;
    private Map<String, Long> lectureFeeList = new HashMap<>();
}
