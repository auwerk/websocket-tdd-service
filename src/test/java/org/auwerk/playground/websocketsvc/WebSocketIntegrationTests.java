package org.auwerk.playground.websocketsvc;

import org.auwerk.playground.websocketsvc.config.WebSocketServiceProperties;
import org.auwerk.playground.websocketsvc.service.MessageSenderService;
import org.auwerk.playground.websocketsvc.testutil.TestLatchTextWebSocketHandler;
import org.auwerk.playground.websocketsvc.testutil.TestWebSocketSessionContainer;
import org.auwerk.playground.websocketsvc.testutil.TestWebSocketSessionContainerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTests {
	private static final String TEST_PAYLOAD = "test payload";

	@Autowired
	private TestWebSocketSessionContainerFactory sessionContainerFactory;
	private TestWebSocketSessionContainer<TestLatchTextWebSocketHandler> sessionContainer;
	@Autowired
	private MessageSenderService messageSenderService;

	@Before
	public void before() throws Exception {
		sessionContainer = sessionContainerFactory.createContainer(new TestLatchTextWebSocketHandler(), 500);
	}

	@After
	public void after() throws Exception {
		if (sessionContainer != null) {
			sessionContainer.close();
		}
	}

	@Test
	public void connectivityTest() {
	}

	@Test
	public void mockingTest() {
		Assert.assertFalse(messageSenderService.testMocking());
	}

	@Test
	public void basicTest() throws Exception {
		sessionContainer.getSession().sendMessage(new TextMessage(TEST_PAYLOAD));
		Assert.assertEquals(TEST_PAYLOAD, sessionContainer.getHandler().getMessage(500));
	}

	@TestConfiguration
	@EnableConfigurationProperties(WebSocketServiceProperties.class)
	public static class WebSocketIntegrationTestsConfiguration {
		private WebSocketServiceProperties properties;
		private Integer localServerPort;

		public WebSocketIntegrationTestsConfiguration(WebSocketServiceProperties properties) {
			this.properties = properties;
		}

		@Bean
		@Primary
		public MessageSenderService messageSenderServiceMock() {
			// Mock won't be sending anything
			return Mockito.mock(MessageSenderService.class);
		}

		@EventListener
		public void onServletContainerInitialized(WebServerInitializedEvent event) {
			localServerPort = event.getApplicationContext().getWebServer().getPort();
		}

		@Bean
		public WebSocketClient webSocketClient() {
			return new StandardWebSocketClient();
		}

		@Bean
		@Lazy
		public TestWebSocketSessionContainerFactory sessionContainerFactory() {
			var factory = new TestWebSocketSessionContainerFactory(webSocketClient());
			factory.setEndpointUri(properties.getEndpointUri());
			factory.setServerPort(localServerPort);
			return factory;
		}
	}

}
