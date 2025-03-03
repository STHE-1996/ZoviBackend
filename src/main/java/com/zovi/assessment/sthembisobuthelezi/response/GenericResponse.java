package com.zovi.assessment.sthembisobuthelezi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    private String responseMessage;

    private String developerMessage;

    private String responseCode;
    private String data;

    public LoginResponse setRole(String role) {
        return null;
    }
}