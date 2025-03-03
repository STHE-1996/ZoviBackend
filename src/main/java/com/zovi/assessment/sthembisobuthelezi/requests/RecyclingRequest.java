package com.zovi.assessment.sthembisobuthelezi.requests;

import lombok.Data;

@Data
public class RecyclingRequest {
    private String userId;
    private String location;
    private String type;
    private String availability;
}
