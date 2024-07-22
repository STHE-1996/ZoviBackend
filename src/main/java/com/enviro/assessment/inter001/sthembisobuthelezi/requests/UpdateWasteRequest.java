package com.enviro.assessment.inter001.sthembisobuthelezi.requests;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import lombok.Data;

@Data
public class UpdateWasteRequest {
    private String userId;
    private String wasteId;
    private String type;
    private String quantity;
    private UserRole userRole;
}
