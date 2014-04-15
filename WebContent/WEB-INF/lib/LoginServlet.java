package com.apigateway.adminutility.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.apigateway.adminutility.restresources.GatewayProxy;
import com.apigateway.adminutility.utils.GatewayProxyHelper;

public class LoginServlet extends HttpServlet {
	
	private static final Logger logger=LogManager.getLogger(LoginServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Entering doGet");
		
		request.getSession().invalidate();
		logger.info("session invalidated");
		
		Cookie[] cookies = request.getCookies();
		
		logger.debug(cookies.toString());
		
		for(Cookie cookie : cookies){
			if(cookie.getName().equalsIgnoreCase("loginCookie")){
				cookie.setValue("false");
				response.addCookie(cookie);
			}
		}
		
		logger.debug("redirected to: "+request.getContextPath());
		response.sendRedirect(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Entering doPost");
		
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = request.getReader();
		String line;
		while((line = bufferedReader.readLine() ) != null){
			logger.debug("Line: "+line);
			stringBuilder.append(line);
		}
		JSONObject array=(JSONObject) JSONValue.parse(stringBuilder.toString());
		String token = array.get("token").toString();
		String env = array.get("env").toString();
		logger.debug("token: "+token+" "+"env: "+env);
		
		String propertiesPath = request.getSession().getServletContext().getInitParameter("propertiesfile");
		Properties properties = new Properties();
        properties.load(request.getSession().getServletContext().getResourceAsStream(propertiesPath));
        String baseUrl;
        if(env.equalsIgnoreCase("live")){
        	baseUrl = properties.getProperty("live.environment.url");
        } else {
        	baseUrl = properties.getProperty("ref.environment.url");
        }
		
		GatewayProxyHelper gatewayProxyHelper = new GatewayProxyHelper();
		boolean validUser = gatewayProxyHelper.loginWorker(token, baseUrl);
		if(validUser == true){
			logger.info("User validity:"+validUser);
			HttpSession session = request.getSession(true);
			session.setAttribute("token",token);
			session.setAttribute("baseUrl",baseUrl);
			response.setStatus(200);
		} else {
			logger.error("User validity:"+validUser);
			response.setStatus(401);
		}
	}

}
