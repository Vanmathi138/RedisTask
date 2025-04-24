package com.app.SpringBoot_Redis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.SpringBoot_Redis.dto.OtpDto;
import com.app.SpringBoot_Redis.service.OtpService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OtpController {
	private final OtpService otpService;
	
	@GetMapping({"/","/home","/status"})
	public String home() {
		return "Welcome to onboard";
	}
	
	@PostMapping("/generate")
	public ResponseEntity<?> generate(@RequestParam String email){
		System.out.println("Sending OTP to: [" + email + "]");
		email = email.trim();
		String otp = otpService.generateOtp(email);
		System.out.println(otp);
		return ResponseEntity.ok("Successfully Otp Generated! Please Check your mail"); 
	}
	
	@PostMapping("/validate")
	public ResponseEntity<?> validate(@RequestBody OtpDto otpDto){
		try {
			otpService.validateOtp(otpDto);
			return ResponseEntity.ok("Successfully Verified Otp");
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
