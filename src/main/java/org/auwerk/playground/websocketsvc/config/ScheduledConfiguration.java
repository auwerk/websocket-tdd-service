package org.auwerk.playground.websocketsvc.config;

import org.auwerk.playground.websocketsvc.service.MessageSenderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class ScheduledConfiguration implements SchedulingConfigurer {
	
	@Bean
	public MessageSenderService messageSenderService() {
		return new MessageSenderService();
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
