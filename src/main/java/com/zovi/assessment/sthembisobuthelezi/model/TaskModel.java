package com.zovi.assessment.sthembisobuthelezi.model;

import com.zovi.assessment.sthembisobuthelezi.enums.TaskStatus;
import com.zovi.assessment.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class TaskModel {
    @Id
    private String id;
    private String taskName;
    private String description;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "due_date")
    private String dueDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private String date;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @ManyToOne
    @JoinColumn(name = "user_model_id")  // foreign key pointing to UserModel
    private UserModel userModel;
    private LocalDateTime timerStartTime;
    private LocalDateTime timerEndTime;
    private boolean timerRunning;


    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
