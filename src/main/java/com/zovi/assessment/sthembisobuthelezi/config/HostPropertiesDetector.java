package com.zovi.assessment.sthembisobuthelezi.config;

import org.springframework.stereotype.Component;

@Component
public class HostPropertiesDetector {
    private static final String HOSTNAME = System.getenv("WEBSITE_HOSTNAME") != null ?
            System.getenv("WEBSITE_HOSTNAME") : "localhost:"+System.getenv("server.port");
    private static final String SLOT_NAME = System.getenv("WEBSITE_SLOT_NAME");

    public static String getSwaggerAPIPathSelector(){

        System.out.println("HOST NAME: "+ HOSTNAME);
        System.out.println("SLOT NAME: "+ SLOT_NAME);
        System.out.println("====================================");

        return "/api/**";
    }
}
