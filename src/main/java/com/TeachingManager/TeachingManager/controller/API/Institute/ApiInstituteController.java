package com.TeachingManager.TeachingManager.controller.API.Institute;

import com.TeachingManager.TeachingManager.DTO.Institute.AddInstituteRequest;
import com.TeachingManager.TeachingManager.DTO.Institute.InstituteInfo;
import com.TeachingManager.TeachingManager.DTO.Institute.InstituteResponse;
import com.TeachingManager.TeachingManager.DTO.Institute.UpdateInstituteRequest;

import com.TeachingManager.TeachingManager.Service.User.CustomUserDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteServiceImpl;
import com.TeachingManager.TeachingManager.config.exceptions.AlreadyRegisteredException;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;


@Controller
@RequiredArgsConstructor
public class ApiInstituteController {
    private final InstituteServiceImpl instService;
    private final InstituteDetailServiceImpl instituteDetailService;
    private final CustomUserDetailServiceImpl userDetailService;

    // 학원 추가

    @PostMapping("/api/institute")

    public ResponseEntity<Map<String, String>> signup(@RequestBody AddInstituteRequest request) {
        String email = request.getEmail();
        Map<String, String> response = new HashMap<>();

        try {
            CustomUser user = userDetailService.loadCustomUserByUsername(email);
            // 이미 존재하는 사용자가 있는 경우
            response.put("error", "Already Registered");
            response.put("message", "이미 가입된 계정입니다");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
        } catch (UsernameNotFoundException e) {
            // 계정이 존재하지 않는 경우 새로 생성
            instService.register(request);
            response.put("message", "생성됨!");
            return ResponseEntity.ok().body(response); // 200 OK
        }
    }



    // 요청한 학원 정보 조회
    @GetMapping("/api/institute")
    public ResponseEntity<InstituteInfo> search_Institute( @AuthenticationPrincipal CustomUser user){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT" ))){

            Institute institute = (Institute) instituteDetailService.loadUserByPk(user.getPk());
            InstituteInfo instituteInfo = new InstituteInfo(institute);
            instituteInfo.setInstitute_email(institute.getEmail());

            return ResponseEntity.ok().body(instituteInfo);
        }
        else if(user instanceof Teacher){

            Institute institute = (Institute) instituteDetailService.loadUserByPk(((Teacher) user).getInstitutePk());
            InstituteInfo instituteInfo = new InstituteInfo(institute);

            return ResponseEntity.ok().body(instituteInfo);
        }
        return ResponseEntity.badRequest().body(null);
    }

    // 학원 정보 수정
    @PutMapping("/api/institute")
    public ResponseEntity<InstituteResponse> update_Institute(@RequestBody UpdateInstituteRequest request, @AuthenticationPrincipal CustomUser user){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            Institute institute = instService.update_Institute(user, request);
            return ResponseEntity.ok()
                    .body(new InstituteResponse(institute));

        }
        return ResponseEntity.badRequest().build();
    }

    // 회원탈퇴 api
    @PutMapping("/api/delete/institute")
    public ResponseEntity<Object> delete_Institute(@AuthenticationPrincipal CustomUser user){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            instService.delete_Institute(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
