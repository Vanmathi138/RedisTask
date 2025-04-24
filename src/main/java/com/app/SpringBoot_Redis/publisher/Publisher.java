package com.app.SpringBoot_Redis.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.SpringBoot_Redis.dto.Product;


@RestController
public class Publisher {
	@Autowired
	private RedisTemplate template;
	@Autowired
	private ChannelTopic topic;
	
	@PostMapping("/publisher")
	public String publisher(@RequestBody Product product) {
	    template.convertAndSend(topic.getTopic(), product.toString());
	    return "Message sent";
	}

	
	@PostMapping("/publish")
	public void publish(String message) {
	    template.convertAndSend(topic.getTopic(), message);
	    //return "Message sent";
	}
//
//	public void publish(String message) {
//		// TODO Auto-generated method stub
//		
//	}
	

}
