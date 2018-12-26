package org.auwerk.playground.websocketsvc.testutil;

import java.io.Closeable;
import java.io.IOException;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestWebSocketSessionContainer<T extends WebSocketHandler> implements Closeable {
	private final T handler;
	private final WebSocketSession session;
	
	@Override
	public void close() throws IOException {
		if (session != null) {
			session.close();
		}
	}
}
