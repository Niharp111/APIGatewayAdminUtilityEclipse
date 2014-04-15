package com.apigateway.adminutility.utils;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GatewayProxyHelper {
	
	private static final Logger logger=LogManager.getLogger(GatewayProxyHelper.class.getName());

	public boolean loginWorker(String token, String baseUrl) throws IOException {
		logger.info("**Entering loginWorker**");
		logger.debug("token: "+token+" "+"baseurl: "+baseUrl);
		Response response = worker(token, MethodTypes.GET, null, getClient()
				.target(baseUrl.concat("client/")));
		return response.getStatus() == HttpServletResponse.SC_OK;
	}

	public Response sessionWorker(HttpServletRequest request, int methodType,
			String targetResource, String payload) {
		
		logger.info("**Entering sessionWorker**");
		String token = request.getSession().getAttribute("token").toString();
		String baseUrl = request.getSession().getAttribute("baseUrl")
				.toString();
		logger.debug("token: "+token+" "+"baseurl: "+baseUrl);
		return worker(token, methodType, payload,
				getClient().target(baseUrl.concat(targetResource)));
	}

	 Client getClient() {
		return ClientBuilder.newBuilder()
				.hostnameVerifier(new HostnameVerifier() {

					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
				}).build();
	}

	private Response worker(String token, int methodType, String payload,
			WebTarget webTarget) {
		
		logger.info("**Entering worker**");
		logger.debug("Token: "+token+" "+"Method Type: "+methodType+" "+"Payload: "+payload);
		
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("Authorization", token);
		Response response = null;
		switch (methodType) {
		case MethodTypes.GET:
			response = invocationBuilder.get();
			break;
		case MethodTypes.POST:
			Entity<String> jsonPostEntity = Entity.entity(payload,
					MediaType.APPLICATION_JSON);
			response = invocationBuilder.post(jsonPostEntity);
			break;
		case MethodTypes.PUT:
			Entity<String> jsonPutEntity = Entity.entity(payload,
					MediaType.APPLICATION_JSON);
			response = invocationBuilder.put(jsonPutEntity);
			break;
		case MethodTypes.DELETE:
			response = invocationBuilder.delete();
			break;
		default:
			// TODO Handle it
			break;
		}
		
		logger.debug("Response: "+response.toString());
		return response;
	}

}