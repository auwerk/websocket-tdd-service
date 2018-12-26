package org.auwerk.playground.websocketsvc.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class BroadcastWebSocketHandler extends AbstractWebSocketHandler {
	private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	public void broadcastMessage(WebSocketMessage<?> message) throws IOException {
		for (WebSocketSession session : sessions) {
			session.sendMessage(message);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
