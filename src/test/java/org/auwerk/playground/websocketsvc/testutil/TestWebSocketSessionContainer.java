package org.auwerk.playground.websocketsvc.testutil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestWebSocketSessionContainer<T extends TextWebSocketHandler> {
	private final T handler;
	private final CompletableFuture<WebSocketSession> sessionFuture;

	public WebSocketSession getSession() throws Exception {
		return sessionFuture.get(500, TimeUnit.MILLISECONDS);
	}
}
