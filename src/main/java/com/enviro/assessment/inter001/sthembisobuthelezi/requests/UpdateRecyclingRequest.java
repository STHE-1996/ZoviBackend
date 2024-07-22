package com.enviro.assessment.inter001.sthembisobuthelezi.requests;

import lombok.Data;

@Data
public class UpdateRecyclingRequest {
    private String userId;
    private String recyclingId;
    private String location;
    private String type;
    private String availability;
}
