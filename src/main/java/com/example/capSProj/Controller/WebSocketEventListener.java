package com.example.capSProj.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.capSProj.Model.ChatMessage;

public class WebSocketEventListener {
	
	  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	    @Autowired
	    private SimpMessageSendingOperations messagingTemplate;

	    @EventListener
	    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
	        logger.info("Received a new web socket connection");
	    }

	    @EventListener
	    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

	        String username = (String) headerAccessor.getSessionAttributes().get("username");
	        if(username != null) {
	            logger.info("User Disconnected : " + username);

	            ChatMessage chatMessage = new ChatMessage();
	            chatMessage.setType(ChatMessage.MessageType.LEAVE);
	            chatMessage.setSender(username);

	            messagingTemplate.convertAndSend("/topic/public", chatMessage);
	        }
	    }
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.setApplicationDestinationPrefixes("/app");

	        // Enables RabbitMQ
	        registry.enableStompBrokerRelay("/topic")
	                .setRelayHost("localhost")
	                .setRelayPort(61613)
	                .setClientLogin("guest")
	                .setClientPasscode("guest");
	    }

}
