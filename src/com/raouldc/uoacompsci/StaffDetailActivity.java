package com.raouldc.uoacompsci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class StaffDetailActivity extends Activity {
	private String name,address,email,url,phone;
	private byte[] photo;
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
		((ImageView)findViewById(R.id.staffDetailPic)).setImageBitmap(bm);
		
		
		StaffDetailListView lv = new StaffDetailListView(R.id.phonelistView1);
		
		
		
		
		
		

//		TextView tw = (TextView) findViewById(R.id.staffName);
//		tw.setText(phone);
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
	
	
	public class StaffDetailListView extends ListActivity {
	    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	    ArrayList<String> listItems=new ArrayList<String>();

	    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	    ArrayAdapter<String> adapter;

	    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
	    int clickCounter=0;
	    int _id;
	    
	    public StaffDetailListView(int id)
	    {
	    	_id = id;
	    }

	    @Override
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        setContentView(R.layout.activity_staff_detail);
	        listItems.add("Clicked : "+clickCounter++);
	        adapter=new ArrayAdapter<String>(this,
	            _id,
	            listItems);
	        setListAdapter(adapter);
	    }

	    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	    public void addItems(View v) {
	        listItems.add("Clicked : "+clickCounter++);
	        adapter.notifyDataSetChanged();
	    }
	    
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	     // TODO Auto-generated method stub
	     //super.onListItemClick(l, v, position, id);
	    	addItems(v);
	    }
	}
}
