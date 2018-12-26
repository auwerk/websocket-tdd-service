package org.auwerk.playground.websocketsvc.config;

import java.io.IOException;

import org.auwerk.playground.websocketsvc.service.MessageSenderService;
import org.auwerk.playground.websocketsvc.service.MessageEchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class ScheduledConfiguration implements SchedulingConfigurer {
	
	private final WebSocketServiceProperties properties;

	@Autowired
	private MessageEchoService handler;
	
	public ScheduledConfiguration(WebSocketServiceProperties properties) {
		this.properties = properties;
	}

	@Bean
	public MessageSenderService messageSenderService() {
		return new MessageSenderService(handler, properties.getPeriodicMessagePayload());
	}

	@Scheduled(fixedDelay = 2000)
	public void scheduledMessageSend() throws IOException {
		messageSenderService().periodicSend();
	}

	@Bean
	public TaskScheduler serverTaskScheduler() {
		var taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(3);
		taskScheduler.setThreadNamePrefix("app-timer-");
		taskScheduler.setDaemon(true);
		return taskScheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(serverTaskScheduler());
	}

}
