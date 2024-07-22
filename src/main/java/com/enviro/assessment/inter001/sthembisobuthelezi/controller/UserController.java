package com.enviro.assessment.inter001.sthembisobuthelezi.controller;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.HttpStatusEnum;
import com.enviro.assessment.inter001.sthembisobuthelezi.response.GenericResponse;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.LoginRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RegistrationRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.response.LoginResponse;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

@RequestMapping("/api")
public class UserController {
    @Autowired
    private Userservice userservice;
    @PostMapping("/RegisterUser")
    public ResponseEntity<GenericResponse> RegisterUser(@RequestBody RegistrationRequest request){
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


    @GetMapping("/User/{userId}")
    public Optional<UserModel> User(@PathVariable String userId) {
        return userservice.User(userId);
    }
}
