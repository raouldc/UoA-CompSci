package com.raouldc.uoacompsci;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Staff implements Comparable<Staff>,Serializable{

	//Vcard fields: Tel(Work and Fax), Name, Fullname,Address, Title, email, organisation, url, photo(jpg)
	private String _name, _fullname,_upi, _tel_work, _tel_fax, _email, _organisation,  _url, _address;
	private byte[] _photo;
	/**
	 * @param _name
	 * @param _fullname
	 * @param _upi
	 * @param _tel_work
	 * @param _tel_fax
	 * @param _email
	 * @param _organisation
	 * @param _url
	 * @param _photo
	 */
	public Staff(String _name, String _fullname, String _upi, String _tel_work,
			String _tel_fax, String _email, String _organisation, String _url,
			byte[] _photo, String _address) {
		this._name = _name;
		this._fullname = _fullname;
		this._upi = _upi;
		this._tel_work = _tel_work;
		this._tel_fax = _tel_fax;
		this._email = _email;
		this._organisation = _organisation;
		this._url = _url;
		this._photo = _photo;
		this._address =_address;
	}
	
//	public Staff(Parcel in)
//	{
//		readFromParcel(in);
//	}
	

	public byte[] get_photo() {
		return _photo;
	}


	public String get_upi() {
		return _upi;
	}

	public String get_name() {
		return _name;
	}



	public String get_email() {
		return _email;
	}


	@Override
	public String toString()
	{
		return _name;
		
	}


	@Override
	public int compareTo(Staff arg0) {
		// TODO Auto-generated method stub
		return this._name.toLowerCase().compareTo(arg0._name.toLowerCase());
	}

	public String get_fullname() {
		return _fullname;
	}

	public String get_tel_work() {
		return _tel_work;
	}

	public String get_url() {
		return _url;
	}

	public String get_address() {
		return _address;
	}


//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//	@Override
//	public void writeToParcel(Parcel arg0, int arg1) {
//		// TODO Auto-generated method stub
//		arg0.writeString(_name);
//		arg0.writeString(_fullname);
//		arg0.writeString(_upi);
//		arg0.writeString(_tel_work);
//		arg0.writeString(_tel_fax);
//		arg0.writeString(_email);
//		arg0.writeString(_organisation);
//		arg0.writeString(_url);
//		arg0.writeString(_address);
//		arg0.writeByteArray(_photo);
//	}
//	
//	private void readFromParcel(Parcel in) {
//		 
//		// We just need to read back each
//		// field in the order that it was
//		// written to the parcel
//		_name = in.readString();
//		_fullname = in.readString();
//		_upi = in.readString();
//		_tel_work= in.readString();
//		_tel_fax= in.readString();
//		_email= in.readString();
//		_organisation= in.readString();
//		_url= in.readString();
//		_address= in.readString();
//		try {
//			in.readByteArray(_photo);
//		} catch (NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	public static final Parcelable.Creator CREATOR =
//	    	new Parcelable.Creator() {
//	            public Staff createFromParcel(Parcel in) {
//	                return new Staff(in);
//	            }
//	 
//	            public Staff[] newArray(int size) {
//	                return new Staff[size];
//	            }
//	        };
}
