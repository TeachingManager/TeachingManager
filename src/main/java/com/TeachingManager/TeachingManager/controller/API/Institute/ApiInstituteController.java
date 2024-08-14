package com.TeachingManager.TeachingManager.controller.API.Institute;

import com.TeachingManager.TeachingManager.DTO.Institute.AddInstituteRequest;
import com.TeachingManager.TeachingManager.DTO.Institute.InstituteResponse;
import com.TeachingManager.TeachingManager.DTO.Institute.UpdateInstituteRequest;

import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteServiceImpl;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ApiInstituteController {
    private final InstituteServiceImpl instService;

    // 학원 추가
    @PostMapping("/api/institute")
    public String signup(AddInstituteRequest request){
        instService.register(request);
        return "redirect:/login";
    }

    // 학원 정보 수정
    @PutMapping("/api/institute") ResponseEntity<InstituteResponse> update_Institute(@RequestBody UpdateInstituteRequest request, @AuthenticationPrincipal CustomUser user){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            Institute institute = instService.update_Institute(user, request);
            return ResponseEntity.ok()
                    .body(new InstituteResponse(institute));

        }
        return ResponseEntity.badRequest().build();
    }

    // 회원탈퇴 api
    @PutMapping("/api/delete/institute") ResponseEntity<String> delete_Institute(@AuthenticationPrincipal CustomUser user){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            return ResponseEntity.ok()
                    .body(instService.delete_Institute(user));
        }
        return ResponseEntity.badRequest().build();
    }

}
