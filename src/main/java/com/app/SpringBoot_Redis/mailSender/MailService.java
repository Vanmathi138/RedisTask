package com.app.SpringBoot_Redis.mailSender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender mailSender;
	
	public void sendMail(String email, String otp) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Your Otp");
		mail.setText("Your Otp is: "+otp);
		mailSender.send(mail);
	}

}
