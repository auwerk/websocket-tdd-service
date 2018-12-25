package org.auwerk.playground.websocketsvc.testutil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TestLatchTextWebSocketHandler extends TextWebSocketHandler {
	private CountDownLatch latch = new CountDownLatch(1);
	private String message;

	public String getMessage(long timeoutMillis) throws InterruptedException {
		latch.await(timeoutMillis, TimeUnit.MILLISECONDS);
		return message;
	}

	public void resetLatch() {
		message = null;
		latch = new CountDownLatch(1);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		this.message = message.getPayload();
		latch.countDown();
	}
}
