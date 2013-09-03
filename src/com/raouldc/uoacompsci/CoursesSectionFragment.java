package com.raouldc.uoacompsci;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CoursesSectionFragment extends ListFragment implements
		ActionBar.TabListener {

	private ListFragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from fragment_courses.xml
		getActivity().setContentView(R.layout.fragment_courses);
		
		String[] values = new String[] { "CS 101", "CS 102", "CS 103"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
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
