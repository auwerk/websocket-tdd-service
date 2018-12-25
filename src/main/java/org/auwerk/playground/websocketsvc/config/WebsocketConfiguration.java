package org.auwerk.playground.websocketsvc.config;

import org.auwerk.playground.websocketsvc.service.MyTextHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myTextHandler(), "/myHandler");
	}
	
	@Bean
	public MyTextHandler myTextHandler() {
		return new MyTextHandler();
	}

}
