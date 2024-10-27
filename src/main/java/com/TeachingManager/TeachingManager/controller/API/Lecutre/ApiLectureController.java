package com.TeachingManager.TeachingManager.controller.API.Lecutre;

import com.TeachingManager.TeachingManager.DTO.Lecture.AddLectureRequest;
import com.TeachingManager.TeachingManager.DTO.Lecture.LectureResponse;
import com.TeachingManager.TeachingManager.DTO.Lecture.UpdateLectureRequest;
import com.TeachingManager.TeachingManager.Service.Lecture.LectureService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiLectureController {

    private final LectureService lectureService;

    @PostMapping("/api/lectures")
    public ResponseEntity<Lecture> createLecture(@AuthenticationPrincipal CustomUser user, @RequestBody AddLectureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lectureService.registerLecture(request, user));
    }

    @GetMapping("/api/lectures")
    public ResponseEntity<List<LectureResponse>> findAllLecture(@AuthenticationPrincipal CustomUser user) {
        List<Lecture> lectureList = lectureService.findAll(user);
        List<LectureResponse> response = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            LectureResponse r = new LectureResponse(lecture);
            response.add(r);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/lectures/{id}")
    public ResponseEntity<LectureResponse> findLecture(@AuthenticationPrincipal CustomUser user, @PathVariable Long id) {
        LectureResponse response = new LectureResponse(lectureService.findLecture(user, id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/lectures/{id}")
    public ResponseEntity<LectureResponse> updateLecture(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateLectureRequest request, @PathVariable Long id) {
        LectureResponse response = new LectureResponse(lectureService.updateLecture(request, user, id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/delete/lectures/{id}")
    public ResponseEntity<String> deleteLecture(@AuthenticationPrincipal CustomUser user, @PathVariable Long id) {
        return ResponseEntity.ok(lectureService.deleteLecture(user, id));
    }

}
