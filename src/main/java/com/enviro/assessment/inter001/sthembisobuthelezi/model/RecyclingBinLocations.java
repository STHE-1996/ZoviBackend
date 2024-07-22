package com.enviro.assessment.inter001.sthembisobuthelezi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class RecyclingBinLocations {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String location;
    private String type;
    private String availability;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
