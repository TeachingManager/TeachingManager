package com.TeachingManager.TeachingManager.controller;

import com.TeachingManager.TeachingManager.Service.Lecture.LectureService;
import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.form.LectureForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/Lecture")
    public String Lecture() {
        return "lecture/lectureMain";
    }

    @GetMapping("/Lecture/new")
    public String createLectureForm() {
        return "lecture/lectureCreate";
    }

    @PostMapping("/Lecture/new")
    public String lectureCreate(LectureForm form) {
//        Lecture lecture = new Lecture();
//        lecture.setName(form.getName());
//        lecture.setStartTime(form.getStartTime());
//        lecture.setEndTime(form.getEndTime());
//        lecture.setCategory(form.getCategory());
//        lecture.setGrade(form.getGrade());
//        lecture.setFee(form.getFee());

//        lectureService.registerLecture(lecture);

        return "redirect:/Lecture";
    }
}
