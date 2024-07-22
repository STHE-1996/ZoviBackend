package com.enviro.assessment.inter001.sthembisobuthelezi.requests;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import lombok.Data;

@Data
public class WasteRequest {

    private String userId;
    private String type;
    private String quantity;
    private UserRole userRole;
}
