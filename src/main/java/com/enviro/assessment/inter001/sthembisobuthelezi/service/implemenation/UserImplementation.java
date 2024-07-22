package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.repository.UserRepository;
import com.enviro.assessment.inter001.sthembisobuthelezi.security.JwtUtil;
import com.enviro.assessment.inter001.sthembisobuthelezi.security.PasswordUtil;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.CommonService;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.Userservice;
import com.enviro.assessment.inter001.sthembisobuthelezi.model.UserModel;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.LoginRequest;
import com.enviro.assessment.inter001.sthembisobuthelezi.requests.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserImplementation implements Userservice {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CommonService commonService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public UserModel RegisterUser(RegistrationRequest request){
        Optional<UserModel> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if (user.isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
         UserModel userModel = new UserModel();
         userModel.setName(request.getName());
         userModel.setSurname(request.getSurname());
         userModel.setEmail(request.getEmail());
         userModel.setUserRole(request.getUserRole());
         commonService.passwordMatchesConfirmation(request.getPassword(), request.getConfirmPassword());
//         passwordUtil.encode(request.getPassword());
         userModel.setPassword(request.getPassword());
        return userRepository.save(userModel);
    }

    @Override
    public List<UserModel> users(){
        return userRepository.findAll();
    }


    @Override
    public Optional<Map<String, String>> login(LoginRequest request) {
        Optional<UserModel> userModel = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));

        if (userModel.isPresent()) {
            if (request.getPassword().equals(userModel.get().getPassword())) {
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.generateToken(userModel.get().getId());
                String role = String.valueOf(userModel.get().getUserRole());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("role", role);
                return Optional.of(response);
            } else {
                throw new RuntimeException("Password is incorrect");
            }
        }
        return Optional.empty();
    }


    @Override
    public Optional<UserModel> User(String userId){
        Optional<UserModel> userModel = userRepository.findById(userId);
        if (userModel.isPresent()){
            return userModel;
        }
        return userModel;
    }

}
