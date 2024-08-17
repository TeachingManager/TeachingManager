package com.TeachingManager.TeachingManager.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequest {
    private String institute_email;
    private String teacher_email;
}
