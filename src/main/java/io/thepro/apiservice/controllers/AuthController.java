package io.thepro.apiservice.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ImportUserRecord;
import io.thepro.apiservice.RegisterRequest;
import io.thepro.apiservice.security.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.thepro.apiservice.security.SecurityUtils;
import io.thepro.apiservice.security.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	private Claims claims = new Claims();

	@Autowired
	SecurityUtils securityUtils;

	@PostMapping("/login")
	public User login() {
		return securityUtils.getPrincipal();
	}

	@PostMapping("/register")
	public void register(RegisterRequest registerRequest) throws FirebaseAuthException {
		claims.createUser("test", "kanek899@gmail.com");
		claims.createUser(registerRequest.getDisplayName(), registerRequest.getEmail());
	}

}
