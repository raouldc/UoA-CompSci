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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class StaffSectionFragment extends ListFragment implements
		ActionBar.TabListener {
	private ListFragment mFragment;
	private ArrayAdapter<Staff> adapter;
	private ArrayList<Staff> staffList;

	public StaffSectionFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get the view from fragment_courses.xml
		getActivity().setContentView(R.layout.fragment_staff);
		staffList = new ArrayList<Staff>();
		adapter = new ArrayAdapter<Staff>(getActivity(),
					android.R.layout.simple_list_item_1, staffList);
		StaffTask t = new StaffTask(getActivity(),adapter);
		t.execute(getResources().getString(R.string.staff_xml_url));

		setListAdapter(adapter);
	}
	
	private class StaffTask extends AsyncTask<String,Void,ArrayList<Staff>> {
		private Context context;
		private ArrayAdapter<Staff> _adap;
		public StaffTask(Context c, ArrayAdapter<Staff> adap){
			context = c;
			_adap = adap;
		}

		@Override
		protected ArrayList<Staff> doInBackground(String... params) {
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
				Staff staff = null;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String tagname = parser.getName();
					switch (eventType) {
					case XmlPullParser.START_TAG:
						if (tagname.equalsIgnoreCase("Person")) {
							// create a new instance of employee
							staff = new Staff();
						}
						break;

					case XmlPullParser.TEXT:
						text = parser.getText();
						break;

					case XmlPullParser.END_TAG:
						if (tagname.equalsIgnoreCase("Person")) {
							// add employee object to list
							staffList.add(staff);
						} else if (tagname.equalsIgnoreCase("uPIField")) {
							staff.set_name(text);
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
			return staffList;
		}
		
	    @Override
	    protected void onPostExecute(ArrayList<Staff> staffList){
	        _adap.notifyDataSetChanged();
	    }

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
        mFragment = new StaffSectionFragment();
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
