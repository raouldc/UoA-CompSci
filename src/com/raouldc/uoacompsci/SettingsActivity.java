package com.raouldc.uoacompsci;

import java.io.File;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Preference button = (Preference) findPreference("deleteCache");
		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				// clear the cache
				File staffFile = new File(getCacheDir() + "/staffList");
				File coursesFile = new File(getCacheDir() + "/courseList");
				// check if cache exists
				if (!staffFile.exists() && !coursesFile.exists()) {
					Toast.makeText(getApplicationContext(),
							"Cache has already been cleared", Toast.LENGTH_LONG)
							.show();
				} else {
					staffFile.delete();
					coursesFile.delete();
					Toast.makeText(getApplicationContext(),
							"Cache is now cleared", Toast.LENGTH_LONG).show();
				}
				return true;
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivityForResult(myIntent, 0);
		return true;

	}
}
