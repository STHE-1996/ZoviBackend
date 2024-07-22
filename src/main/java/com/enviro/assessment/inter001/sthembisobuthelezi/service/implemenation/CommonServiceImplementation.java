package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.service.CommonService;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImplementation implements CommonService {


    @Override
    public boolean passwordMatchesConfirmation(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }


}
