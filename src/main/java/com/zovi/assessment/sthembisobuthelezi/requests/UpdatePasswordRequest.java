package com.zovi.assessment.sthembisobuthelezi.requests;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String email;
    private String otpPin;
    private String newPassword;
    private String confirmNewPassword;
}
