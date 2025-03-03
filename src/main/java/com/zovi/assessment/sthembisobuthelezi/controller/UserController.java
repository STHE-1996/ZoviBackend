package com.zovi.assessment.sthembisobuthelezi.controller;

import com.zovi.assessment.sthembisobuthelezi.enums.HttpStatusEnum;
import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;
import com.zovi.assessment.sthembisobuthelezi.requests.ForgotPasswordRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.UpdatePasswordRequest;
import com.zovi.assessment.sthembisobuthelezi.response.GenericResponse;
import com.zovi.assessment.sthembisobuthelezi.model.UserModel;
import com.zovi.assessment.sthembisobuthelezi.requests.LoginRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.RegistrationRequest;
import com.zovi.assessment.sthembisobuthelezi.response.LoginResponse;
import com.zovi.assessment.sthembisobuthelezi.service.Userservice;
import com.zovi.assessment.sthembisobuthelezi.service.implemenation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

@RequestMapping("/api")
public class UserController {
    @Autowired
    private Userservice userservice;

    @PostMapping("/RegisterUser")
    public ResponseEntity<GenericResponse> RegisterUser(@RequestBody RegistrationRequest request) throws IOException {
        UserModel userModel= userservice.RegisterUser(request);
        return ResponseEntity.ok(new GenericResponse()
                .setResponseCode(HttpStatusEnum.SUCCESSFUL.getCode())
                .setDeveloperMessage(HttpStatusEnum.SUCCESSFUL.getDeveloperMessage())
                .setResponseMessage("Successfully Registered in")
                .setData(String.valueOf(userModel.getId())));
    }

    @GetMapping("/AllUsers")
    public List<UserModel> AllUsers(){
        return userservice.users();
    }

//    @PostMapping("/login")
//    public Optional<UserModel> login(@RequestBody LoginRequest request){
//        return userservice.login(request);
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<Map<String, String>> loginResult = userservice.login(request);

        if (loginResult.isPresent()) {
            Map<String, String> result = loginResult.get();
            String token = result.get("token");
            String role = result.get("role");

            return ResponseEntity.ok(new LoginResponse()
                    .setResponseCode(HttpStatusEnum.SUCCESSFUL.getCode())
                    .setDeveloperMessage(HttpStatusEnum.SUCCESSFUL.getDeveloperMessage())
                    .setResponseMessage("Successfully Logged in")
                    .setData(token)
                    .setRole(role));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((LoginResponse) new LoginResponse()
                    .setResponseCode(HttpStatusEnum.UNAUTHORIZED.getCode())
                    .setDeveloperMessage(HttpStatusEnum.UNAUTHORIZED.getDeveloperMessage())
                    .setResponseMessage("Login failed"));
        }
    }

    @PostMapping("/VerifyAccount")
    public ResponseEntity<?> verifyAccount(@RequestBody Map<String, String> requestBody) {
        String enteredPin = requestBody.get("enteredPin");
        System.out.println("Received PIN: " + enteredPin);

        try {
            String verificationMessage = String.valueOf(userservice.verifyAccountMethod(enteredPin));
            return ResponseEntity.ok(verificationMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("The account you are trying to verify does not exist"));
        }
    }



    @GetMapping("/User/{userId}")
    public Optional<UserModel> User(@PathVariable String userId) {
        return userservice.User(userId);
    }

    private Map<String, String> createErrorResponse(String errorMessage) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", errorMessage);
        return errorResponse;
    }


    @PostMapping("/ForgotPassword")
    public ResponseEntity<?> ForgotPassword(@RequestBody ForgotPasswordRequest request) throws IOException {
        UserImplementation.ForgotPasswordResponse response = userservice.forgotPassword(request.getEmail());

        return ResponseEntity.ok(new GenericResponse()
                .setResponseCode(HttpStatusEnum.SUCCESSFUL.getCode())
                .setDeveloperMessage(HttpStatusEnum.SUCCESSFUL.getDeveloperMessage())
                .setResponseMessage(response.getMessage()));
    }

    @PostMapping("/UpdatePassword")
    public ResponseEntity<?> UpdatePassword(@RequestBody UpdatePasswordRequest request) {
        return new ResponseEntity<>(userservice.updatePassword(request.getEmail(), request.getOtpPin(), request.getNewPassword(), request.getConfirmNewPassword()), HttpStatus.OK);
    }


    @GetMapping("/getAllWasteForStaff")
    public List<TaskModel> getAllWaste() {
        return userservice.getAllTask();
    }


    @GetMapping("/getAllUser")
    public List<UserModel> getAllUser() {
        return userservice.getAllUser();
    }
}
