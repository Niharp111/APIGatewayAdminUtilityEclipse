package com.apigateway.adminutility.restresources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.apigateway.adminutility.utils.GatewayProxyHelper;
import com.apigateway.adminutility.utils.MethodTypes;

@Path("/{targetResource : [a-zA-Z0-9/]+}")
public class GatewayProxy {
	
	private static final Logger logger=LogManager.getLogger(GatewayProxy.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProxy(@Context HttpServletRequest request,
			@PathParam("targetResource") String targetResource) {
		
		
		logger.info("Entering Gatewayproxy.getProxy");
		logger.debug("Target resource: "+targetResource);
		
		Response apiGateWayResponse = new GatewayProxyHelper().sessionWorker(
				request, MethodTypes.GET, targetResource, null);
		
		logger.debug("Response:: "+Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build());
		
		return Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postProxy(@Context HttpServletRequest request,
			String payload, @PathParam("targetResource") String targetResource) {
		
		logger.info("Entering Gatewayproxy.postProxy");
		logger.debug("Target resource: "+targetResource);
		
		Response apiGateWayResponse = new GatewayProxyHelper().sessionWorker(
				request, MethodTypes.POST, targetResource, payload);
		
		logger.debug(Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build());
		
		return Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putProxy(@Context HttpServletRequest request,
			String payload, @PathParam("targetResource") String targetResource) {
		
		logger.info("Entering Gatewayproxy.putProxy");
		logger.debug("Target resource: "+targetResource);
		
		Response apiGateWayResponse = new GatewayProxyHelper().sessionWorker(
				request, MethodTypes.PUT, targetResource, payload);
		
		logger.debug(Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build());
		
		return Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProxy(@Context HttpServletRequest request,
			String payload, @PathParam("targetResource") String targetResource) {
		
		logger.info("Entering Gatewayproxy.deleteProxy");
		logger.debug("Target resource: "+targetResource);
		
		Response apiGateWayResponse = new GatewayProxyHelper().sessionWorker(
				request, MethodTypes.DELETE, targetResource, payload);
		
		logger.debug(Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build());
		
		return Response.status(apiGateWayResponse.getStatus())
				.entity(apiGateWayResponse.readEntity(String.class)).build();
	}

}
