package com.zovi.assessment.sthembisobuthelezi.service.implemenation;

import com.zovi.assessment.sthembisobuthelezi.repository.UserRepository;
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

