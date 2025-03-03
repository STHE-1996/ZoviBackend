package com.zovi.assessment.sthembisobuthelezi.requests;

import com.zovi.assessment.sthembisobuthelezi.enums.TaskStatus;
import com.zovi.assessment.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class TaskRequest {

    private String userId;
    private String taskName;
    private String description;
    private UserRole userRole;
    private Date dueDate;
    private Date startDate;
//    private TaskStatus status;
}
