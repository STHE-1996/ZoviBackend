package com.enviro.assessment.inter001.sthembisobuthelezi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.enviro.assessment.inter001.sthembisobuthelezi.model")
@EnableJpaRepositories(basePackages = "com.enviro.assessment.inter001.sthembisobuthelezi.repository")
@PropertySource("classpath:en-messages.properties")
public class SthembisobutheleziApplication {

	public static void main(String[] args) {
		SpringApplication.run(SthembisobutheleziApplication.class, args);
	}

}
