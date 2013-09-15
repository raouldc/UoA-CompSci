package com.raouldc.uoacompsci;

import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.raouldc.uoacompsci.rss.RssItem;
import com.raouldc.uoacompsci.rss.RssReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeSectionFragment extends Fragment implements
		ActionBar.TabListener {
	private Fragment mFragment;
	private View mView;
	private Activity local;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home, container, false);

		HomeScreenImageTask imgT = new HomeScreenImageTask(getActivity());
		imgT.execute(getResources().getString(R.string.home_image_url));

		local = getActivity();
		GetRSSDataTask task = new GetRSSDataTask();

		// Start download RSS task
		task.execute(getResources().getString(R.string.news_rss_feed));
		GetRSSDataTask task2 = new GetRSSDataTask();
		task2.execute(getResources().getString(R.string.events_rss_feed));
		GetRSSDataTask task3 = new GetRSSDataTask();
		task3.execute(getResources().getString(R.string.seminars_rss_feed));
		return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public class ListListener implements OnItemClickListener {
		// Our listener will contain a reference to the list of RSS Items
		// List item's reference
		List<RssItem> listItems;
		// And a reference to a calling activity
		// Calling activity reference
		Activity activity;

		/** We will set those references in our constructor. */
		public ListListener(List<RssItem> aListItems, Activity anActivity) {
			listItems = aListItems;
			activity = anActivity;
		}

		/** Start a browser with url from the rss item. */
		@Override
		public void onItemClick(AdapterView parent, View view, int pos, long id) {
			// We create an Intent which is going to display data
			Intent i = new Intent(Intent.ACTION_VIEW);
			// We have to set data for our new Intent
			i.setData(Uri.parse(listItems.get(pos).getLink()));
			// And start activity with our Intent
			activity.startActivity(i);
		}
	}

	private class HomeScreenImageTask extends AsyncTask<String, Void, Bitmap> {
		private Context context;

		public HomeScreenImageTask(Context c) {
			context = c;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];

			// make an HTTP request
			Bitmap bitmap = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(url);
				// get the response
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				byte[] content = EntityUtils.toByteArray(httpEntity);
				bitmap = BitmapFactory.decodeByteArray(content, 0,
						content.length);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bmp) {
			if (isVisible()) {
				ImageView img = (ImageView) mView.findViewById(R.id.homeImage);
				img.setImageBitmap(bmp);
			}
		}

	}

	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
		String url = "";

		@Override
		protected List<RssItem> doInBackground(String... urls) {

			// Debug the task thread name
			try {
				// Create RSS reader
				url = urls[0];
				RssReader rssReader = new RssReader(url);

				// Parse RSS, get items
				List<RssItem> x = rssReader.getItems();
				return rssReader.getItems();

			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<RssItem> result) {
			if (isVisible()) {
				ListView itcItems = null;
				// Get a ListView from main view
				if (url.equals(getResources().getString(R.string.news_rss_feed))) {
					itcItems = (ListView) local.findViewById(R.id.newslistView);
				} else if (url.equals(getResources().getString(
						R.string.seminars_rss_feed))) {
					itcItems = (ListView) local
							.findViewById(R.id.seminarslistView);
				} else if (url.equals(getResources().getString(
						R.string.events_rss_feed))) {
					itcItems = (ListView) local
							.findViewById(R.id.eventslistView);
				}

				RSSListAdapter rAdap = new RSSListAdapter(result);

				// Set list adapter for the ListView
				itcItems.setAdapter(rAdap);

				// Set list view item click listener
				itcItems.setOnItemClickListener(new ListListener(result, local));
			}
		}
	}

	private class RSSListAdapter extends BaseAdapter {
		private List<RssItem> _items;

		public RSSListAdapter(List<RssItem> result) {
			_items = result;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return _items.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View V = convertView;

			if (V == null) {
				LayoutInflater vi = (LayoutInflater) local
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				V = vi.inflate(R.layout.rss_list_item, null);

			}
			TextView title = (TextView) V.findViewById(R.id.itemTitle);
			TextView date = (TextView) V.findViewById(R.id.itemDate);
			TextView description = (TextView) V
					.findViewById(R.id.itemDescription);

			title.setText(_items.get(position).getTitle());
			date.setText(_items.get(position).getDate());
			description.setText(_items.get(position).getDescription());

			return V;
		}

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mFragment = new HomeSectionFragment();
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
