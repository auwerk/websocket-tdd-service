package org.auwerk.playground.websocketsvc.config;

import org.auwerk.playground.websocketsvc.service.MyTextHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableConfigurationProperties(WebSocketServiceProperties.class)
public class WebSocketConfiguration implements WebSocketConfigurer {

	private WebSocketServiceProperties properties;

	public WebSocketConfiguration(WebSocketServiceProperties properties) {
		this.properties = properties;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myTextHandler(), properties.getEndpointUri());
	}

	@Bean
	public MyTextHandler myTextHandler() {
		return new MyTextHandler();
	}

}
