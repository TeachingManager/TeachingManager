package com.TeachingManager.TeachingManager.DTO.Institute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateInstituteRequest {
    private String institute_name;
    private String address;
    private String phoneNum;
}
