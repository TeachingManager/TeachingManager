package com.TeachingManager.TeachingManager.DTO.Teacher;

import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SocialTeacherInfo {
    private UUID teacher_id;
    private String provider;
    private Boolean ifExistOthers;

    public SocialTeacherInfo(Teacher teacher, String provider){
        this.teacher_id = teacher.getPk();
        this.provider = teacher.getProvider();
        if(teacher.getProvider().equals(provider)){
            this.ifExistOthers = false;
        }
        else {
            this.ifExistOthers = true;
        }
    }
}



