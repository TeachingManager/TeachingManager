package com.TeachingManager.TeachingManager.DTO.Institute;

import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstituteResponse {

    private Long institute_id;
    private String institute_name;
    private String address;
    private String phoneNum;

    public InstituteResponse(Institute institute){
        this.institute_id = institute.getPk();
        this.institute_name = institute.getInstitute_name();
        this.address = institute.getAddress();
        this.phoneNum = institute.getPhoneNum();
    }

}
