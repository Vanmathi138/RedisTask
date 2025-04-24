package com.app.SpringBoot_Redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.app.SpringBoot_Redis.subscriber.Receiver;
import com.app.SpringBoot_Redis.subscriber.SseController;
@Configuration
public class RedisConfig {
	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("localhost");
		configuration.setPort(6379);
		return new JedisConnectionFactory(configuration);
	}
	
	@Bean
	public RedisTemplate<String, Object> template(){
		RedisTemplate<String, Object> template=new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
	
	@Bean
	public ChannelTopic channelTopic() {
		return new ChannelTopic("pubsub:notification");
	}
	@Bean
	public Receiver receiver(SseController controller) {
	    return new Receiver(controller);
	}

	@Bean
	public MessageListenerAdapter redisMessageListenerAdapter(Receiver receiver) {
	    return new MessageListenerAdapter(receiver);
	}

	@Bean
	public RedisMessageListenerContainer listenerContainer(MessageListenerAdapter messageListenerAdapter) {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory());
	    container.addMessageListener(messageListenerAdapter, channelTopic());
	    return container;
	}


}
//@Configuration(enforceUniqueMethods = false)
//public class RedisConfig {
//	@Bean
//	public JedisConnectionFactory connectionFactory() {
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//		configuration.setHostName("localhost");
//		configuration.setPort(6379);
//		return new JedisConnectionFactory(configuration);
//	}
//	
//	@Bean
//	public RedisTemplate<String, Object> template(){
//		RedisTemplate<String, Object> template=new RedisTemplate<>();
//		template.setConnectionFactory(connectionFactory());
//		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//		return template;
//	}
//	
//	@Bean
//	public ChannelTopic channelTopic() {
//		return new ChannelTopic("pubsub:notification");
//	}
//	
////	@Bean
////	public MessageListenerAdapter messageListenerAdapter() {
////		return new MessageListenerAdapter(new Receiver());
////	}
//	
//	@Bean
//	public Receiver receiver(SseController controller) {
//	    return new Receiver(controller);
//	}
//
//	@Bean
//	public MessageListenerAdapter redisMessageListenerAdapter(Receiver receiver) {
//	    return new MessageListenerAdapter(receiver);
//	}
//
//	@Bean
//	public RedisMessageListenerContainer listenerContainer(MessageListenerAdapter messageListenerAdapter) {
//	    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//	    container.setConnectionFactory(connectionFactory());
//	    container.addMessageListener(messageListenerAdapter, channelTopic());
//	    return container;
//	}
//
//
//}
