package com.TeachingManager.TeachingManager.controller.API.Attend;

import com.TeachingManager.TeachingManager.DTO.Attend.AttendInfo;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApiAttendController {

    @PostMapping("/api/attend/{pk}")
    public ResponseEntity<AttendInfo> Detail_Attend(@PathVariable("pk") Long pk, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok().build();
    }
}
