package com.enviro.assessment.inter001.sthembisobuthelezi.service;

import java.io.IOException;

public interface CommonService {
    boolean passwordMatchesConfirmation(String password, String confirmPassword);

    String sendingEmailForForgotPassword(String emailTo, String firstName, String pin) throws IOException;

    String generatePinAndAssociate(String phoneNumber);

    String sendingEmailForVerification(String emailTo, String firstName, String pin) throws IOException;

    boolean isValidEmail(String email);

    String getMessage(String key, Object... args);

    void isValidPassword(String password);
}
