package com.enviro.assessment.inter001.sthembisobuthelezi.model;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class PickupSchedulingModel {
    @Id
    private String id;
    private String date;
    private String time;
    private String status;
    private UserRole userRole;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
