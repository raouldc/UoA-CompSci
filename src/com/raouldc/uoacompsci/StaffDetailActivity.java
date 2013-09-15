package com.raouldc.uoacompsci;

import java.util.ArrayList;
import java.util.Arrays;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class StaffDetailActivity extends Activity {
	private String name, address, email, url, phone;
	private byte[] photo;
	private ListView phonelv;
	private ListView addresslv;
	private ListView emaillv;
	private ListView urllv;

	/* Item click listener for the phone item click event. */
	private transient OnItemClickListener phonelist1ClickListener = new OnItemClickListener() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 * .widget.AdapterView, android.view.View, int, long)
		 */

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			String tempPhone = phone.replace("x", ",");
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+tempPhone));
			startActivity(callIntent);

		}
	};

	/* Item click listener for the address item click event. */
	private transient OnItemClickListener addresslvlist1ClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
		}
	};

	/* Item click listener for the email item click event. */
	private transient OnItemClickListener emaillvlist1ClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});		  
			emailIntent.setType("message/rfc822");
			startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
		}
	};
	
	/* Item click listener for the url item click event. */
	private transient OnItemClickListener urllvlist1ClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			if (!url.equals("None"))
			{
				// We create an Intent which is going to display data
				Intent i = new Intent(Intent.ACTION_VIEW);
				// We have to set data for our new Intent
				i.setData(Uri.parse(url));
				// And start activity with our Intent
				startActivity(i);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_detail);
		// Show the Up button in the action bar.

		Bundle b = getIntent().getExtras();
		name = b.getString("name");
		address = b.getString("address");
		email = b.getString("email");
		photo = b.getByteArray("photo");
		url = b.getString("url");
		phone = b.getString("phone");

		//
		Bitmap bm = BitmapFactory.decodeByteArray(photo, 0, photo.length);
		((ImageView) findViewById(R.id.staffDetailPic)).setImageBitmap(bm);

		phonelv = (ListView) findViewById(R.id.phonelistView1);
		addresslv = (ListView) findViewById(R.id.addressListView1);
		emaillv = (ListView) findViewById(R.id.emailListView1);
		urllv = (ListView) findViewById(R.id.websiteListView1);

		phonelv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>(
						Arrays.asList(phone))));
		addresslv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>(
						Arrays.asList(address))));
		emaillv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>(
						Arrays.asList(email))));
		urllv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>(
						Arrays.asList(url))));
		
		phonelv.setOnItemClickListener(phonelist1ClickListener);
		addresslv.setOnItemClickListener(addresslvlist1ClickListener);
		emaillv.setOnItemClickListener(emaillvlist1ClickListener);
		urllv.setOnItemClickListener(urllvlist1ClickListener);

		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(name);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.staff_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
