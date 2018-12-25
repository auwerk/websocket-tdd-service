package org.auwerk.playground.websocketsvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class MessageSenderService {

	@Autowired
	private MyTextHandler myTextHandler;

	@Scheduled(fixedDelay = 2000)
	public void periodicSend() {
		myTextHandler.broadcastMessage("Hello, world!");
	}

}
