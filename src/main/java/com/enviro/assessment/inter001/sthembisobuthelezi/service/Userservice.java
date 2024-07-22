package com.enviro.assessment.inter001.sthembisobuthelezi.service;

import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.LoginRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RegistrationRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Userservice {
    UserModel RegisterUser(RegistrationRequest request);

    List<UserModel> users();


    Optional<Map<String, String>> login(LoginRequest request);

    Optional<UserModel> User(String userId);
}
