package com.apigateway.adminutility.utils;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GatewayProxyHelperTest {
	@Spy
	GatewayProxyHelper gatewayProxyHelper;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpSession session;

	@Mock
	HttpServletResponse response;

	@Mock
	Client client;

	@Mock
	WebTarget webTarget;

	@Mock
	Invocation.Builder invocationBuilder;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	}

	private void setupSessionWorker() {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("token")).thenReturn("token");
		when(session.getAttribute("baseUrl")).thenReturn("baseURL");
		doReturn(client).when(gatewayProxyHelper).getClient();

		when(client.target("baseURL/client")).thenReturn(webTarget);
		when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(
				invocationBuilder);
	}

	@Test
	public void sessionWorkerHeaderTest() {
		setupSessionWorker();

		gatewayProxyHelper.sessionWorker(request, 0, "/client", null);

		verify(client).target("baseURL/client");
		verify(invocationBuilder).header("Authorization", "token");

	}

	@Test
	public void sessionWorkerGetRequestTest() {
		setupSessionWorker();

		gatewayProxyHelper.sessionWorker(request, 0, "/client", null);

		verify(client).target("baseURL/client");
		verify(invocationBuilder).header("Authorization", "token");

		verify(invocationBuilder).get();

	}

	@Test
	public void sessionWorkerPostRequestTest() {
		setupSessionWorker();

		gatewayProxyHelper.sessionWorker(request, 1, "/client", null);

		verify(client).target("baseURL/client");
		verify(invocationBuilder).header("Authorization", "token");

		verify(invocationBuilder).post((Entity<?>) Matchers.anyObject());

	}

}
