package com.app.SpringBoot_Redis.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.app.SpringBoot_Redis.dto.OtpDto;
import com.app.SpringBoot_Redis.mailSender.MailService;

@Service
public class OtpService {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private MailService mailService;

	public String generateOtp(String email) {
		String otp = String.valueOf(new Random().nextInt(90000) + 10000);
		redisTemplate.opsForValue().set(email, otp, Duration.ofMinutes(5));
		mailService.sendMail(email, otp);
		
		return otp;
	}

	public String validateOtp(OtpDto otp) {
		Object savedotp = redisTemplate.opsForValue().get(otp.getEmail());
		
		if(savedotp == null) {
			throw new RuntimeException("OTP is expired. Please generate a new OTP");
		}
		if(!otp.getOtp().equals(savedotp)) {
			throw new RuntimeException("OTP is invalid");
		}
		redisTemplate.delete(otp.getEmail());
		return "Successfully Verified!";
	}

}
