package org.auwerk.playground.websocketsvc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "websocket-svc")
public class WebSocketServiceProperties {

	private String endpointUri;

}
