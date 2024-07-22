package com.enviro.assessment.inter001.sthembisobuthelezi.model;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class WasteModel {
    @Id
    private String id;
    private String type;
    private String quantity;
    private String date;
    private UserRole userRole;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
