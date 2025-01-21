package com.enviro.assessment.inter001.sthembisobuthelezi.service;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.WasteModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.LoginRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RegistrationRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation.UserImplementation;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Userservice {
    UserModel RegisterUser(RegistrationRequest request) throws IOException;

    List<UserModel> users();


    Optional<Map<String, String>> login(LoginRequest request);

    Optional<UserModel> User(String userId);

    UserImplementation.ForgotPasswordResponse forgotPassword(String email) throws IOException;

    ResponseEntity<?> verifyAccountMethod(String enteredPin);

    String updatePassword(String email, String ptpPin, String newPassword, String confirmNewPassword);

    List<WasteModel> getAllWaste();
}
