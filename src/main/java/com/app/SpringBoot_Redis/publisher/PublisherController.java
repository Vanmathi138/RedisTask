package com.app.SpringBoot_Redis.publisher;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.SpringBoot_Redis.dto.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PublisherController {
	private final Publisher publisher;
	
	@PostMapping("/post-product")
	public ResponseEntity<String> send(@RequestBody Product product){
		String message = "You have a new notification from publisher: "+product.getName();
		publisher.publish(message);
		return ResponseEntity.ok("Product posted and notification sent.");
	}

}
