package com.example.springsecurityauthwithh2;

import com.example.springsecurityauthwithh2.auth.AuthenticationService;
import com.example.springsecurityauthwithh2.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.springsecurityauthwithh2.user.Role.ADMIN;
import static com.example.springsecurityauthwithh2.user.Role.MANAGER;

@SpringBootApplication
public class SpringSecurityAuthWithH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthWithH2Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin));


			var manager = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager));
		};
	}

}
