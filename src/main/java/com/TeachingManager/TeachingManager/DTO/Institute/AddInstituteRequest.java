package com.TeachingManager.TeachingManager.DTO.Institute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInstituteRequest {
    private String email;
    private String password;
    private String insName;
    private String address;
    private String phoneNum;
}
