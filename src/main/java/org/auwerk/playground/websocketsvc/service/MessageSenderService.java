package org.auwerk.playground.websocketsvc.service;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageSenderService {
	private final BroadcastWebSocketHandler handler;
	private final String periodicMessagePayload;

	public void periodicSend() throws IOException {
		handler.broadcastMessage(new TextMessage(periodicMessagePayload));
	}
	
	public boolean testMocking() {
		return true; // mock will return false
	}

}
