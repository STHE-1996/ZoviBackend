package com.zovi.assessment.sthembisobuthelezi.service.implemenation;

import com.zovi.assessment.sthembisobuthelezi.enums.HttpStatusEnum;
import com.zovi.assessment.sthembisobuthelezi.exception.CustomeException;
import com.zovi.assessment.sthembisobuthelezi.model.TaskModel;

import com.zovi.assessment.sthembisobuthelezi.repository.UserRepository;
import com.zovi.assessment.sthembisobuthelezi.security.JwtUtil;
import com.zovi.assessment.sthembisobuthelezi.security.PasswordUtil;
import com.zovi.assessment.sthembisobuthelezi.service.CommonService;
import com.zovi.assessment.sthembisobuthelezi.service.Userservice;
import com.zovi.assessment.sthembisobuthelezi.model.UserModel;
import com.zovi.assessment.sthembisobuthelezi.requests.LoginRequest;
import com.zovi.assessment.sthembisobuthelezi.requests.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class UserImplementation implements Userservice {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CommonService commonService;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public UserModel RegisterUser(RegistrationRequest request) throws IOException {
        Optional<UserModel> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if (user.isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
         UserModel userModel = new UserModel();
         userModel.setName(request.getName());
         userModel.setSurname(request.getSurname());
         userModel.setEmail(request.getEmail());
         userModel.setGender(request.getGender());
         userModel.setUserRole(request.getUserRole());
         commonService.passwordMatchesConfirmation(request.getPassword(), request.getConfirmPassword());
//         passwordUtil.encode(request.getPassword());
         userModel.setPassword(request.getPassword());

        String pin = commonService.generatePinAndAssociate(request.getEmail());
        userModel.setPin(pin);

         commonService.sendingEmailForVerification(request.getEmail(), request.getName(), pin);
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

    @Override
    public ForgotPasswordResponse forgotPassword(String email) throws IOException {
        // Find user by email
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel != null) {
            String pin = commonService.generatePinAndAssociate(email);

            // Send an email to the user with the PIN
            commonService.sendingEmailForForgotPassword(userModel.getEmail(), userModel.getName(), pin);

            userModel.setPin(pin);

            userRepository.save(userModel);
            // Return a successful response
            return new ForgotPasswordResponse("The reset link has been sent to your email.");
        } else {
            // If the user is not found, throw an exception with a meaningful message
            throw new RuntimeException("No account found with the provided email address.");
        }
    }

    public class ForgotPasswordResponse {
        private String message;

        public ForgotPasswordResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }



    @Override
    public ResponseEntity<?> verifyAccountMethod(String enteredPin) {
        Optional<UserModel> userModelOptional = userRepository.findByPin(enteredPin);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            if (userModel.getVerificationStatus().equals(true)) {
                // This block will execute if the account is already verified
                log.warn("Verification failed: Account already verified");
                throw new CustomeException().setDeveloperMessage(HttpStatusEnum.EMAIL_INVALID.getDeveloperMessage())
                        .setResponseCode(500)
                        .setResponseMessage("The account is already verified. Please log in.")
                        .setStatusCode(HttpStatus.NOT_FOUND);
            } else {
                // This block will execute if the account is not verified
                log.info("Verification successful for user: {}", userModel.getSurname());

                // Update verification status
                userModel.setVerificationStatus("true");

                // Save the updated UserModel to the database
                userRepository.save(userModel);

                return ResponseEntity.ok("Verification successful. Welcome to ZionApp");
            }
        } else {

            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.EMAIL_INVALID.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage("The account you are trying to verify does not exist")
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public String updatePassword(String email, String ptpPin, String newPassword, String confirmNewPassword) {
        boolean emailValidation = commonService.isValidEmail(email);
        Optional<UserModel> userModelOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userModelOptional.isPresent()) {
            UserModel userModel1 = userModelOptional.get();
            if (userModel1.getPin().equals(ptpPin)) {
                commonService.isValidPassword(newPassword);
                if (commonService.passwordMatchesConfirmation(newPassword, confirmNewPassword)) {
                    userModel1.setPassword(newPassword);
                    userRepository.save(userModel1);
                } else {
                    throw new CustomeException().setDeveloperMessage(HttpStatusEnum.PASSWORD_DO_NOT_MATCH.getDeveloperMessage())
                            .setResponseCode(500)
                            .setResponseMessage(commonService.getMessage(HttpStatusEnum.PASSWORD_DO_NOT_MATCH.getKey()))
                            .setStatusCode(HttpStatus.NOT_FOUND);

                }
            } else {

                throw new RuntimeException("The account does not exist");
//                throw new CustomeException().setDeveloperMessage(HttpStatusEnum.ACCOUNT_NOT_EXIST.getDeveloperMessage())
//                        .setResponseCode(500)
//                        .setResponseMessage(commonService.getMessage(HttpStatusEnum.ACCOUNT_NOT_EXIST.getKey()))
//                        .setStatusCode(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.PIN_INCORRECT.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage(commonService.getMessage(HttpStatusEnum.PIN_INCORRECT.getKey()))
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }
        return "You have successfully generated the new password, click the button to login";
    }



    @Override
    public List<TaskModel> getAllTask() {
        return userRepository.findAllTask();
    }

    @Override
    public List<UserModel> getAllUser() {
        return userRepository.findAll();
    }


    

}
