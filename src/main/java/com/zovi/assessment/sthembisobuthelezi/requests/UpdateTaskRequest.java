package com.zovi.assessment.sthembisobuthelezi.requests;

import com.zovi.assessment.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {
    private String userId;
    private String taskId;
    private String taskName;
    private String description;
    private String dueDate;
    private UserRole userRole;
}
