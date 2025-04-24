//package com.app.SpringBoot_Redis.subscriber;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//
//import lombok.RequiredArgsConstructor;
//@RequiredArgsConstructor
//public class Receiver implements MessageListener {
//
//    Logger logger = LoggerFactory.getLogger(Receiver.class);
//
//    private final SseController sseController;
////
////    public Receiver(SseController sseController) {
////        this.sseController = sseController;
////    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String data = message.toString();
//        logger.info("Consumed event {}", data);
//        sseController.sendToClients(data);
//    }
//    
////    public void handleMessage(String message) {
////    	sseController.sendNotification(message);
////    }
//    
//}
package com.app.SpringBoot_Redis.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Receiver implements MessageListener {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final SseController sseController;

    public Receiver(SseController sseController) {
        this.sseController = sseController;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String data = message.toString();
        logger.info("Consumed event {}", data);
        sseController.sendToClients(data);
    }
}
