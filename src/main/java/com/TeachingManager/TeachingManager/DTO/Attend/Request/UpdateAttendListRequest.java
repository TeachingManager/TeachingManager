package com.TeachingManager.TeachingManager.DTO.Attend.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UpdateAttendListRequest {
    private Map<Long, Byte> attendList = new HashMap<>();
}
