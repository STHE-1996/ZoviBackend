package com.enviro.assessment.inter001.sthembisobuthelezi.requests;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.UserRole;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private UserRole userRole;
    private String gender;
    private String password;
    private String confirmPassword;
}
