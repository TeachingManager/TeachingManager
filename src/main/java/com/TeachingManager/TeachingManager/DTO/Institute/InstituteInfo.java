package com.TeachingManager.TeachingManager.DTO.Institute;

import com.TeachingManager.TeachingManager.domain.Institute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituteInfo
{
    private String institute_email = null;
    private String institute_name;
    private String address;
    private String phoneNum;

    public InstituteInfo(Institute institute) {
        this.institute_name = institute.getInstitute_name();
        this.address = institute.getAddress();
        this.phoneNum = institute.getPhoneNum();
    }
}
