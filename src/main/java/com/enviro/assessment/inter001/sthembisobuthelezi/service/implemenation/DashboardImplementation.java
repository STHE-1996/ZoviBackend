package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardImplementation {
    @Autowired
    private UserRepository userRepository;

    public long countMales() {
        return userRepository.countByGender("Male");
    }

    public long countFemales() {
        return userRepository.countByGender("Female");
    }
}

