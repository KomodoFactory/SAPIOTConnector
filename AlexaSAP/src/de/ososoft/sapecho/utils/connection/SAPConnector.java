package de.ososoft.sapecho.utils.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class SAPConnector {

	private String webserviceURL;
	private HttpClient client;

	public SAPConnector() {
		webserviceURL = "http://saped1.bemaso.com:8010/sap/bc/z_alexa_banf";
		client = HttpClientBuilder.create().build();
	}
	
	public boolean testConnection() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(webserviceURL);
		HttpResponse response = client.execute(request);
		request.releaseConnection();
		int status = response.getStatusLine().getStatusCode();
		if(status == 501) {
			return true;
		}
		return false;
		
	}

	public HttpPost createPostRequest(String json) throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(webserviceURL);
		StringEntity params = new StringEntity(json);
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		return request;
	}

	public HttpResponse executeRequest(HttpEntityEnclosingRequestBase request)
			throws ClientProtocolException, IOException {
		HttpResponse response = client.execute(request);
		request.releaseConnection();
		return response;
	}
}
