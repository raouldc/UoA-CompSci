package com.raouldc.uoacompsci;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLParser {
	private String _url;

	public XMLParser(String url) {

		_url = url;
	}

	public ArrayList<Course> parseCourse() throws ClientProtocolException,
			IOException, XmlPullParserException {

		ArrayList<Course> courseList = new ArrayList<Course>();
		// make an HTTP request
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(_url);
		// get the response
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		String xmlContent = EntityUtils.toString(httpEntity);

		// Create XMLPullParser instance
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();

		parser.setInput(new StringReader(xmlContent));

		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String tagname = parser.getName();
			String text = "";
			Course course = null;
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (tagname.equalsIgnoreCase("Course")) {
					// create a new instance of employee
					course = new Course();
				}
				break;

			case XmlPullParser.TEXT:
				text = parser.getText();
				break;

			case XmlPullParser.END_TAG:
				if (tagname.equalsIgnoreCase("Course")) {
					// add employee object to list
					courseList.add(course);
				} else if (tagname.equalsIgnoreCase("codeField")) {
					course.set_code(text);
				} else if (tagname.equalsIgnoreCase("semesterField")) {
					course.set_semester(text);
				} else if (tagname.equalsIgnoreCase("titleField")) {
					course.set_title(text);
				}
				break;

			default:
				break;
			}
			eventType = parser.next();
		}
		return courseList;
	}

}
