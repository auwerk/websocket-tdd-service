package org.auwerk.playground.websocketsvc.testutil;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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

	public <T extends TextWebSocketHandler> TestWebSocketSessionContainer<T> createContainer(T handler) {
		var uri = String.format("ws://%s:%d%s", serverHost, serverPort, endpointUri);
		return new TestWebSocketSessionContainer<T>(handler, client.doHandshake(handler, uri).completable());
	}
}
