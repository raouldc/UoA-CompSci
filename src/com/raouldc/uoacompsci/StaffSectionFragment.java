package com.raouldc.uoacompsci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
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

		
		
		File file = new File(getActivity().getCacheDir() + "/staffList");
		//check if the cached ArrayList exists
		if (!file.exists()) {
			//if it doesnt exist, pull data from the server
			adapter = new ArrayAdapter<Staff>(getActivity(),
					android.R.layout.simple_list_item_1, staffList);
			StaffTask t = new StaffTask(getActivity(), adapter);
			t.execute(getResources().getString(R.string.staff_xml_url));
		} else {
			//else try to load the file
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream is = new ObjectInputStream(fis);
				Object readObject = is.readObject();
				is.close();
				staffList.clear();
				staffList = (ArrayList) readObject;
				adapter = new ArrayAdapter<Staff>(getActivity(),
						android.R.layout.simple_list_item_1, staffList);
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// if(readObject != null && readObject instanceof Region) {
			// return (Region) readObject;
			// }
		}

		setListAdapter(adapter);

	}

	private class StaffTask extends AsyncTask<String, Void, ArrayList<Staff>> {
		private Context context;
		private ArrayAdapter<Staff> _adap;

		public StaffTask(Context c, ArrayAdapter<Staff> adap) {
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
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser parser = factory.newPullParser();

				parser.setInput(new StringReader(xmlContent));

				int eventType = parser.getEventType();
				String text = "";
				Staff staff;
				String upi = "";
				VCardParser.setHttpClient(httpClient);
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String tagname = parser.getName();
					switch (eventType) {
					// dont need this
					// case XmlPullParser.START_TAG:
					// if (tagname.equalsIgnoreCase("Person")) {
					// // create a new instance of employee
					// staff = new Staff();
					// }
					// break;

					case XmlPullParser.TEXT:
						text = parser.getText();
						break;

					case XmlPullParser.END_TAG:
						if (tagname.equalsIgnoreCase("Person")) {
							// add employee object to list
							// send text to UPI parser and store in List
							Staff s = VCardParser.parse(getResources()
									.getString(R.string.vcard_url) + upi, upi);
							staffList.add(s);
						} else if (tagname.equalsIgnoreCase("uPIField")) {
							upi = text;
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
			
			//write to a file for faster loading in the future
			try {
				FileOutputStream fos = new FileOutputStream(new File(getActivity().getCacheDir() + "/staffList"));
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(staffList);
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return staffList;
		}

		@Override
		protected void onPostExecute(ArrayList<Staff> staffList) {
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
