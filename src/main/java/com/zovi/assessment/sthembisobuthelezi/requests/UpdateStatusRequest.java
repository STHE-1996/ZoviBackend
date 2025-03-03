package com.zovi.assessment.sthembisobuthelezi.requests;

import com.zovi.assessment.sthembisobuthelezi.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    private String id;
    private TaskStatus status;
}
