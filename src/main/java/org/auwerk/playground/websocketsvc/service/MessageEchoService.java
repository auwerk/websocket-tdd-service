package org.auwerk.playground.websocketsvc.service;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageEchoService extends BroadcastWebSocketHandler {

	public void broadcastMessage(String messagePayload) throws IOException {
		broadcastMessage(new TextMessage(messagePayload));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("received message: {}", message.getPayload());
		session.sendMessage(new TextMessage(message.getPayload()));
	}

}
