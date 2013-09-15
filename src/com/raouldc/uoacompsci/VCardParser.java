package com.raouldc.uoacompsci;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.raouldc.uoacompsci.StaffSectionFragment.StaffListAdapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class VCardParser extends
		AsyncTask<ArrayList<Staff>, Void, ArrayList<Staff>> {
	Context myContext;
	private StaffListAdapter _adap;

	public VCardParser(Context c, StaffListAdapter adapter) {
		myContext = c;
		_adap = adapter;
	}

	@Override
	protected ArrayList<Staff> doInBackground(ArrayList<Staff>... params) {
		ArrayList<Staff> staffList = params[0];
		for (Staff staff : staffList) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpPost = new HttpGet(myContext.getResources()
						.getString(R.string.vcard_url) + staff.get_upi());
				// get the response
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				String content = EntityUtils.toString(httpEntity);

				VCard vcard = Ezvcard.parse(content).first();

				staff.set_email(vcard.getEmails().get(0).getValue());

				staff.set_fullname(vcard.getFormattedName().getValue());

				staff.set_name(vcard.getStructuredName().getGiven() + " "
						+ vcard.getStructuredName().getFamily());

				staff.set_tel_work(vcard.getTelephoneNumbers().get(0).getText());

				staff.set_tel_fax(vcard.getTelephoneNumbers().get(1).getText());
				staff.set_address(vcard.getAddresses().get(0)
						.getExtendedAddress());

				// vcard.getTitles().get(0).getValue();

				staff.set_organisation(vcard.getOrganization().getValues()
						.get(0));
				if (!vcard.getUrls().get(0).getValue().equals("")) {
					staff.set_url(vcard.getUrls().get(0).getValue());
				}
				byte[] photo;
				try {
					photo = vcard.getPhotos().get(0).getData();
				} catch (IndexOutOfBoundsException e) {
					AssetManager assetManager = myContext.getAssets();
					InputStream input = assetManager
							.open("ic_contact_picture.png");

					int size = input.available();
					photo = new byte[size];
					input.read(photo);
				}

				staff.set_photo(photo);
				// String photo = vcard.getPhotos().get(0).toString();

				// Vcard fields: Tel(Work and Fax), Name, Fullname,Address,
				// Title,
				// email, organisation, url, photo(jpg)

				FileOutputStream fos = new FileOutputStream(new File(
						myContext.getCacheDir() + "/staffList"));
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(staffList);
				oos.close();

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
		}
		Collections.sort(staffList);
		return staffList;
	}

	@Override
	protected void onPostExecute(ArrayList<Staff> staffList) {
		_adap.notifyDataSetChanged();
	}

}
