package com.raouldc.uoacompsci;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class VCardParser {
	
	private static DefaultHttpClient httpClient;
	
	public static void setHttpClient(DefaultHttpClient h) {
		httpClient = h;
	}

	public static Staff parse(String url,String upi)
	{
		try {
			HttpGet httpPost = new HttpGet(url);
			// get the response
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String content = EntityUtils.toString(httpEntity);
			
			VCard vcard = Ezvcard.parse(content).first();
			
			//Staff staff = new Staff();
			//email
			
			String email =vcard.getEmails().get(0).getValue();
			
			String formattedname = vcard.getFormattedName().getValue();
			
			String tel = vcard.getTelephoneNumbers().get(0).toString();
			
			String fax = vcard.getTelephoneNumbers().get(1).toString();
			String address = vcard.getAddresses().get(0).toString();
			
			String title = vcard.getTitles().get(0).getValue();
			
			String organisation = vcard.getOrganization().getValues().get(0);
			
			String web = vcard.getUrls().get(0).getValue();
			
			//String photo = vcard.getPhotos().get(0).toString();
			
			//Vcard fields: Tel(Work and Fax), Name, Fullname,Address, Title, email, organisation, url, photo(jpg)
			

			return new Staff(formattedname,formattedname,upi,tel,tel,email,organisation,url,null);
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
		return null;
		
	}

}
