package org.auwerk.playground.websocketsvc.testutil;

import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;

import lombok.Getter;
import lombok.Setter;

public class TestWebSocketSessionContainerFactory {
	private final WebSocketClient client;

	@Getter
	@Setter
	private String serverHost = "localhost";
	@Getter
	@Setter
	private Integer serverPort = 80;
	@Getter
	@Setter
	private String endpointUri = "/ws";

	public TestWebSocketSessionContainerFactory(WebSocketClient client) {
		this.client = client;
	}

	public <T extends WebSocketHandler> TestWebSocketSessionContainer<T> createContainer(T handler,
			long sessionTimeoutMillis) throws Exception {
		var uri = String.format("ws://%s:%d%s", serverHost, serverPort, endpointUri);
		return new TestWebSocketSessionContainer<T>(handler,
				client.doHandshake(handler, uri).get(sessionTimeoutMillis, TimeUnit.MILLISECONDS));
	}
}
