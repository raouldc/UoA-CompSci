package com.raouldc.uoacompsci;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class XMLParser {
	String _xmlContent;
	public XMLParser(String url) throws ClientProtocolException, IOException {
		// make an HTTP request
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		// get the response
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		_xmlContent = EntityUtils.toString(httpEntity);

	}

}
