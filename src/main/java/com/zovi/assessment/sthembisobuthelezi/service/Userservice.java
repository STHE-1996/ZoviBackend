package com.zovi.assessment.sthembisobuthelezi.service;

import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.model.UserModel;
import com.zovi.assessment.sthembisobuthelezi.requests.LoginRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.RegistrationRequest;
import com.zovi.assessment.sthembisobuthelezi.service.implemenation.UserImplementation;
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

    List<TaskModel> getAllTask();

    List<UserModel> getAllUser();
}
