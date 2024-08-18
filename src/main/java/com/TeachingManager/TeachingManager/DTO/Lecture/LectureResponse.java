package com.TeachingManager.TeachingManager.DTO.Lecture;

import com.TeachingManager.TeachingManager.domain.Lecture;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;

import java.util.*;

@NoArgsConstructor
@Getter
public class LectureResponse {

    private Long id;
    private String name;
    private String category;
    private String grade;
    private int fee;
    private String time;
    private List<Map<String, String>> convetedTime;
    private List<JSONObject> jsonTime;
    // 학원 외래키 등록 후 테스트
//    private Teacher teacher;
    private UUID teacherId;

    public LectureResponse(Lecture lecture) {
        this.id = lecture.getLecture_id();
        this.name = lecture.getName();
        this.category = lecture.getCategory();
        this.grade = lecture.getGrade();
        this.fee = lecture.getFee();
        this.time = lecture.getTime();
//        this.teacher = lecture.getTeacher();
        this.convetedTime = ConvertTime(lecture.getTime());
        this.jsonTime = ConvertJsonTime(lecture.getTime());
        this.teacherId = lecture.getTeacher().getPk();
    }


    public List<Map<String, String>> ConvertTime(String time) {

        List<Map<String, String>> ct = new ArrayList<>();
        String[] split = time.split(",");
        for (String s : split) {
            String[] arr = s.split("-");
            Map<String, String> convertedTimes = new HashMap<>();
            convertedTimes.put("day", ToKorean(arr[0]));
            convertedTimes.put("time", arr[1]);
            ct.add(convertedTimes);
        }
        return ct;
    }

    public List<JSONObject> ConvertJsonTime(String time) {

        List<JSONObject> result = new ArrayList<>();
        String[] split = time.split(",");
        for(String s : split) {
            String[] arr = s.split("-");
            JSONObject json = new JSONObject();
            json.put("day", ToKorean(arr[0]));
            json.put("time", arr[1]);
            result.add(json);
        }
        return result;
    }

    public String ToKorean(String s) {
        switch (s) {
            case "MONDAY":
                return "월요일";
            case "TUESDAY":
                return "화요일";
            case "WEDNESDAY":
                return "수요일";
            case "THURSDAY":
                return "목요일";
            case "FRIDAY":
                return "금요일";
            case "SATURDAY":
                return "토요일";
            case "SUNDAY":
                return "일요일";
            default:
                return "오류";
        }
    }

    public String Convert(String time) {
        String result;
        String[] split = time.split(",");
        for(String s : split) {

        }
        return "a";
    }

}
