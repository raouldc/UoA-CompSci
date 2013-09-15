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
	public Staff(String _upi) {
		this._upi = _upi;
		this._url = "None";
	}
	
	public byte[] get_photo() {
		return _photo;
	}


	public void set_name(String _name) {
		this._name = _name;
	}

	public void set_fullname(String _fullname) {
		this._fullname = _fullname;
	}

	public void set_upi(String _upi) {
		this._upi = _upi;
	}

	public void set_tel_work(String _tel_work) {
		this._tel_work = _tel_work;
	}

	public void set_tel_fax(String _tel_fax) {
		this._tel_fax = _tel_fax;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public void set_organisation(String _organisation) {
		this._organisation = _organisation;
	}

	public void set_url(String _url) {
		this._url = _url;
	}

	public void set_address(String _address) {
		this._address = _address;
	}

	public void set_photo(byte[] _photo) {
		this._photo = _photo;
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
		if(_name==null)
		{
			return _upi;
		}
		return _name;
		
	}


	@Override
	public int compareTo(Staff arg0) {
		// TODO Auto-generated method stub
		if(_name==null)
		{
			return this._upi.toLowerCase().compareTo(arg0._upi.toLowerCase());
		}
		else
		{
			return this._name.toLowerCase().compareTo(arg0._name.toLowerCase());
		}
		
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
}
