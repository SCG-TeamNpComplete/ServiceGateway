package com.scientificgateway.milestone1;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

public class GoogleAuthHelper {
	/**
	 * Please provide a value for the CLIENT_ID constant before proceeding, set
	 * this up at https://code.google.com/apis/console/
	 */
	private static final String CLIENT_ID = "332831582346-1dak6vk1n7o2kplu1lhb1bjr093pngni.apps.googleusercontent.com";
	/**
	 * Please provide a value for the CLIENT_SECRET constant before proceeding,
	 * set this up at https://code.google.com/apis/console/
	 */
	private static final String CLIENT_SECRET = "r5uG-f77IGEiz-inmJGtW1rE";

	/**
	 * Callback URI that google will redirect to after successful authentication
	 */
	private static final String CALLBACK_URI = "http://localhost:8080/SG_MICROSERVICE_DATAINGESTOR/webapi/home";
	
	
	//private static final String CALLBACK_URI = "http://ec2-35-160-137-157.us-west-2.compute.amazonaws.com:8085/SG_MICROSERVICE_DATAINGESTOR/webapi/home";
	
	
	// start google authentication constants
	private static final Iterable<String> SCOPE = Arrays
			.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email"
					.split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	// end google authentication constants

	private String stateToken;

	private final GoogleAuthorizationCodeFlow flow;

	/**
	 * Constructor initializes the Google Authorization Code Flow with CLIENT
	 * ID, SECRET, and SCOPE
	 */
	public GoogleAuthHelper() {
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET,
				(Collection<String>) SCOPE).build();

		generateStateToken();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope
	 */
	public String buildLoginUrl() {

		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

		return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
	}

	/**
	 * Generates a secure state token
	 */
	private void generateStateToken() {

		SecureRandom sr1 = new SecureRandom();

		stateToken = "google;" + sr1.nextInt();

	}

	/**
	 * Accessor for state token
	 */
	public String getStateToken() {
		return stateToken;
	}

	/**
	 * Expects an Authentication Code, and makes an authenticated request for
	 * the user's profile information
	 * 
	 * @return JSON formatted user profile information
	 * @param authCode
	 *            authentication code provided by google
	 */
	public String getUserInfoJson(final String authCode) throws IOException {

		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		final Credential credential = flow.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();

		return jsonIdentity;

	}
}
