package com.zovi.assessment.sthembisobuthelezi.requests;

import com.zovi.assessment.sthembisobuthelezi.enums.UserRole;
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
