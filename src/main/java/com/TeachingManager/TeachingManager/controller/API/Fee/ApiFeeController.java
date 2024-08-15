package com.TeachingManager.TeachingManager.controller.API.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.Service.Fee.FeeService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiFeeController {
    private final FeeService feeService;
    // 이번 번 달의 전체 수강료 가져오기
    @GetMapping("/api/fee")
    public ResponseEntity<List<EnrollFeeResponse>> findFeeList(
            @AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "year") Short year
            , @RequestParam(value = "month") Short month
    ) {
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            return ResponseEntity.ok().body(feeService.findMonthlyLectureFee(user, year, month));
        }
        return ResponseEntity.badRequest().build();
    }

    // 1년치의 수강료 정보 가져오기
    @GetMapping("/api/fee/year")
    public ResponseEntity<List<EnrollYearFeeResponse>> findYearFee(@AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "year") Short year
            , @RequestParam(value = "month") Short month) {
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            return ResponseEntity.ok().body(feeService.findYearLectureFee(user, year, month));
        }
        return ResponseEntity.badRequest().build();
    }

    // 수강 납부 or 수정 API
    @PutMapping("/api/fee")
    public ResponseEntity<Boolean> purchaseFee(@AuthenticationPrincipal CustomUser user
            , @RequestParam(value = "year") Short year
            , @RequestParam(value = "month") Short month
            , @RequestParam(value = "enroll_id") Long enroll_id
            , @RequestParam(value = "paid_amount") int paid_amount) {
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            return ResponseEntity.ok().body(feeService.purchaseLectureFee(user, enroll_id,paid_amount,year,month));
        }
        return ResponseEntity.badRequest().build();
    }
}
