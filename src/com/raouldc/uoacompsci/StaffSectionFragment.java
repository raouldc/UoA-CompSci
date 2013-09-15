package com.raouldc.uoacompsci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.raouldc.uoacompsci.pinnedheaderlistview.PinnedHeaderListView;
import com.raouldc.uoacompsci.pinnedheaderlistview.SectionedBaseAdapter;

public class StaffSectionFragment extends ListFragment implements
		ActionBar.TabListener {
	private ListFragment mFragment;
	private StaffListAdapter adapter;
	private ArrayList<Staff> staffList;

	private int[] sectionIndexes;
	private String[] sectionHeaders;
	private int sectionCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the view from fragment_courses.xml
		getActivity().setContentView(R.layout.fragment_staff);
		staffList = new ArrayList<Staff>();

		adapter = new StaffListAdapter();
		LayoutInflater inflator = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout) inflator.inflate(
				R.layout.fragment_staff, null);
		PinnedHeaderListView listview = (PinnedHeaderListView) layout
				.findViewById(R.id.pinnedListView);

		File file = new File(getActivity().getCacheDir() + "/staffList");
		// check if the cached ArrayList exists
		if (!file.exists()) {
			// if it doesnt exist, pull data from the server
			adapter = new StaffListAdapter();
			StaffTask t = new StaffTask(getActivity(), adapter);
			t.execute(getResources().getString(R.string.staff_xml_url));
		} else {
			// else try to load the file
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream is = new ObjectInputStream(fis);
				Object readObject = is.readObject();
				is.close();
				staffList.clear();
				staffList = (ArrayList) readObject;
				adapter = new StaffListAdapter();
				initializeSections();
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
		}
		setListAdapter(adapter);
		listview.setAdapter(adapter);
	}

	public class StaffListAdapter extends SectionedBaseAdapter {

		@Override
		public Object getItem(int section, int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int section, int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getSectionCount() {
			return sectionCount;
		}

		@Override
		public int getCountForSection(int section) {
			if (section == sectionCount) {
				return staffList.size() - sectionIndexes[section];
			} else {
				return sectionIndexes[section + 1] - sectionIndexes[section];
			}
		}

		@Override
		public View getItemView(int section, int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			RelativeLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (RelativeLayout) inflator.inflate(R.layout.list_item,
						null);
			} else {
				layout = (RelativeLayout) convertView;
			}
			// set item
			Staff staff = staffList.get(sectionIndexes[section] + position);
			((TextView) layout.findViewById(R.id.listtextItem)).setText(staff
					.toString());
			if (staff.get_photo() != null) {
				Bitmap img = BitmapFactory.decodeByteArray(staff.get_photo(),
						0, staff.get_photo().length);
				((ImageView) layout.findViewById(R.id.staffpic))
						.setImageBitmap(img);
			}
			return layout;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView,
				ViewGroup parent) {
			LinearLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(R.layout.header_item,
						null);
			} else {
				layout = (LinearLayout) convertView;
			}
			// set item
			((TextView) layout.findViewById(R.id.headertextItem))
					.setText(sectionHeaders[section]);
			return layout;
		}

	}

	private class StaffTask extends AsyncTask<String, Void, ArrayList<Staff>> {
		private Context context;
		private StaffListAdapter _adap;

		public StaffTask(Context c, StaffListAdapter adapter) {
			context = c;
			_adap = adapter;
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
							// Staff s = VCardParser.parse(getResources()
							// .getString(R.string.vcard_url) + upi, upi,
							// getActivity());
							Staff s = new Staff(upi);
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
			Collections.sort(staffList);
			// write to a file for faster loading in the future
			initializeSections();
			return staffList;
		}

		@Override
		protected void onPostExecute(ArrayList<Staff> staffList) {
			if (isVisible()) {
				_adap.notifyDataSetChanged();
				VCardParser v = new VCardParser(context, _adap);
				v.execute(staffList);
			}
		}

	}

	public void initializeSections() {
		// find indexes of headers in the list
		sectionIndexes = new int[26];
		sectionHeaders = new String[26];
		sectionHeaders[0] = staffList.get(0).toString().substring(0, 1);
		sectionIndexes[0] = 0;

		sectionCount = 0;
		for (int i = 1; i < staffList.size(); i++) {
			if (staffList.get(i).toString().substring(0, 1)
					.compareTo(sectionHeaders[sectionCount]) >= 1) {
				sectionCount++;
				sectionHeaders[sectionCount] = staffList.get(i).toString()
						.substring(0, 1);
				sectionIndexes[sectionCount] = i;
			}
		}
	}

	@Override
	public void onListItemClick(ListView adapterView, View view,
			int rawPosition, long id) {
		SectionedBaseAdapter adapter;
		if (adapterView.getAdapter().getClass()
				.equals(HeaderViewListAdapter.class)) {
			HeaderViewListAdapter wrapperAdapter = (HeaderViewListAdapter) adapterView
					.getAdapter();
			adapter = (SectionedBaseAdapter) wrapperAdapter.getWrappedAdapter();
		} else {
			adapter = (SectionedBaseAdapter) adapterView.getAdapter();
		}
		int section = adapter.getSectionForPosition(rawPosition);
		int position = adapter.getPositionInSectionForPosition(rawPosition);

		if (position != -1) {
			// onSectionClick(adapterView, view, section, id);
			Staff s = staffList.get(sectionIndexes[section] + position);
			// create new Intent
			Intent nextScreen = new Intent(getActivity(),
					StaffDetailActivity.class);

			// staff parameters
			nextScreen.putExtra("name", s.get_name());
			nextScreen.putExtra("address", s.get_address());
			nextScreen.putExtra("email", s.get_email());
			nextScreen.putExtra("photo", s.get_photo());
			nextScreen.putExtra("url", s.get_url());
			nextScreen.putExtra("phone", s.get_tel_work());
			// start Activity
			startActivity(nextScreen);
		} else {
			// onItemClick(adapterView, view, section, position, id);
		}

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
