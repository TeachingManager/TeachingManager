package com.TeachingManager.TeachingManager.DTO.Fee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeeUpdateResponse {
    private Boolean result;

    public FeeUpdateResponse(Boolean result){
        this.result = result;
    }
}
