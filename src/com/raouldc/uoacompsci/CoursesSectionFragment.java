package com.raouldc.uoacompsci;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CoursesSectionFragment extends ListFragment implements
		ActionBar.TabListener {

	private ListFragment mFragment;
	private ArrayAdapter<Course> adapter;
	private ArrayList<Course> courseList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from fragment_courses.xml
		getActivity().setContentView(R.layout.fragment_courses);
		courseList = new ArrayList<Course>();
		adapter = new ArrayAdapter<Course>(getActivity(),
					android.R.layout.simple_list_item_1, courseList);
		CoursesTask t = new CoursesTask(getActivity(),adapter);
		t.execute(getResources().getString(R.string.course_xml_url));
		
		setListAdapter(adapter);
	}
	
	private class CoursesTask extends AsyncTask<String,Void,ArrayList<Course>> {
		private Context context;
		private ArrayAdapter<Course> _adap;
		public CoursesTask(Context c, ArrayAdapter<Course> adap){
			context = c;
			_adap = adap;
		}

		@Override
		protected ArrayList<Course> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];
			
			// make an HTTP request
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(url);
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
				String text = "";
				Course course = null;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String tagname = parser.getName();
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
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return courseList;
		}
		
	    @Override
	    protected void onPostExecute(ArrayList<Course> courseList){
	        _adap.notifyDataSetChanged();
	    }

	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mFragment = new CoursesSectionFragment();
		// Attach fragment_courses.xml layout
		ft.add(android.R.id.content, mFragment);
		ft.attach(mFragment);

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Remove fragment_courses.xml layout
		ft.remove(mFragment);

	}

}
