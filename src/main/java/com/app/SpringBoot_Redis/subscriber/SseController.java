package com.app.SpringBoot_Redis.subscriber;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SseController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }
    public void sendToClients(String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
    
    @GetMapping("/subscribers")
    public SseEmitter subsriber() {
    	SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    	emitters.add(emitter);
    	
    	emitter.onCompletion(() -> emitters.remove(emitter));
    	emitter.onTimeout(() -> emitters.remove(emitter));
    	
    	return emitter;
    }
    public void sendNotification(String message) {
    	for(SseEmitter emitter: emitters) {
    		try {
    			emitter.send(SseEmitter.event().name("notification").data(message));
    		}catch(IOException e) {
    			emitters.remove(emitter);
    		}
    	}
    }
}
