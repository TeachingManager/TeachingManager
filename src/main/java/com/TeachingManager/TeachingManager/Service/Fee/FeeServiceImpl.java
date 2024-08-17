package com.TeachingManager.TeachingManager.Service.Fee;

import com.TeachingManager.TeachingManager.DTO.Fee.EnrollFeeResponse;
import com.TeachingManager.TeachingManager.DTO.Fee.EnrollYearFeeResponse;
import com.TeachingManager.TeachingManager.Repository.Enroll.EnrollRepository;
import com.TeachingManager.TeachingManager.Repository.Fee.FeeRepository;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Enroll;
import com.TeachingManager.TeachingManager.domain.Fee;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final EnrollRepository enrollRepo;
    private final FeeRepository feeRepo;
    private final InstituteRepository instituteRepo;

    // 이번 달 수강료 정보 모두 가져오기
    @Override
    public List<EnrollFeeResponse> findMonthlyLectureFee(CustomUser user, Short year, Short month) {
        return enrollRepo.findEnrolledFeeByDate(user.getPk(), year, month);
    }

    // 올해 수강료 총액 가져오기
    @Override
    public List<EnrollYearFeeResponse> findYearLectureFee(CustomUser user, Short year, Short month) {
        return feeRepo.findYearFee(user.getPk(), year, month);
    }

    // 수강료 납입액 수정
    @Override
    @Transactional
    public Boolean purchaseLectureFee(CustomUser user, Long enroll_id, int paid_amount, Short year, Short month) {
        // 개인 수강 정보 수정
        Enroll enroll = enrollRepo.findById(user.getPk(), enroll_id).orElseThrow(()->new RuntimeException("존재하지 않거나 접근 불가능한 수강 정보에 접근시도"));
        enroll.updatePaidFee(paid_amount);


        Optional<Fee> feeTuple = feeRepo.findByInstituteDate(user.getPk(), year, month);
        if (feeTuple.isPresent()) {
            feeTuple.get().increaseTotalMonthFee(paid_amount);
        }
        else{ // Fee 없을 경우 에러
            throw  new RuntimeException("납입액 수정을 요청했으면, fee 테이블이 없을리 없다.");
        }
        return enroll.getFullPayment();
    }
}
