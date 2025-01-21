package com.enviro.assessment.inter001.sthembisobuthelezi.model;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@Entity
public class UserModel {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String surname;
    private String email;
    private UserRole userRole;
    private String verificationStatus = "false";
    private String pin;
    private String gender;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WasteModel> listOfWaste;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PickupSchedulingModel> pickupSchedulingModelList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RecyclingBinLocations> recyclingBinLocationsList;


    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
