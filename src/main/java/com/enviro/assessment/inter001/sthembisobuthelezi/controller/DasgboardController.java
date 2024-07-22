package com.enviro.assessment.inter001.sthembisobuthelezi.controller;

import com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation.DashboardImplementation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DasgboardController {

    @Autowired
    private DashboardImplementation userService;

    @GetMapping("/counts")
    public GenderCount getUserCounts() {
        long maleCount = userService.countMales();
        long femaleCount = userService.countFemales();
        return new GenderCount(maleCount, femaleCount);
    }

    @Getter
    public static class GenderCount {
        private final long maleCount;
        private final long femaleCount;

        public GenderCount(long maleCount, long femaleCount) {
            this.maleCount = maleCount;
            this.femaleCount = femaleCount;
        }

    }
}
