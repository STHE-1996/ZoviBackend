package com.zovi.assessment.sthembisobuthelezi.model;

import com.zovi.assessment.sthembisobuthelezi.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "user_model")
public class UserModel {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String verificationStatus = "false";
    private String pin;
    private String gender;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_model_task",
            joinColumns = @JoinColumn(name = "user_model_id"),
            inverseJoinColumns = @JoinColumn(name = "task_model_id")
    )
    private List<TaskModel> listOfTask;
    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
