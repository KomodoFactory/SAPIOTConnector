package de.ososoft.sapecho.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import de.ososoft.sapecho.utils.container.Bestellanforderung;

public class SAPConnector {

	private static final String WEBSERVICE_URL = "http:sap.exampleaddress.com:8010/sap/bc/z_alexa_banf";
	private HttpClient client;

	public SAPConnector() {
		client = HttpClientBuilder.create().build();
	}

	public boolean testConnection() throws IOException {
		HttpGet request = new HttpGet(WEBSERVICE_URL);
		int status = executeRequest(request).getStatusLine().getStatusCode();
		return status == 501;
	}

	public HttpPost createPostRequest(String json) throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(WEBSERVICE_URL);
		request.addHeader("content-type", "application/json");
		request.setEntity(new StringEntity(json));
		return request;
	}

	public HttpResponse executeRequest(HttpRequestBase request) throws IOException {
		HttpResponse response = client.execute(request);
		request.releaseConnection();
		return response;
	}

	public boolean sendOrder(String articel, String quantity) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(new Bestellanforderung(articel, Integer.valueOf(quantity)));
		SAPConnector connector = new SAPConnector();
		HttpPost request = connector.createPostRequest(json);
		HttpResponse response = connector.executeRequest(request);
		return (response.getStatusLine().getStatusCode() == 201);
	}
}
