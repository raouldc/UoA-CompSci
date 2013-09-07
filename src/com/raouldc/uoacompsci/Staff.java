package com.raouldc.uoacompsci;

import java.io.Serializable;

public class Staff implements Serializable{

	//Vcard fields: Tel(Work and Fax), Name, Fullname,Address, Title, email, organisation, url, photo(jpg)
	private String _name, _fullname,_upi, _tel_work, _tel_fax, _email, _organisation,  _url,  _photo;
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
			String _photo) {
		this._name = _name;
		this._fullname = _fullname;
		this._upi = _upi;
		this._tel_work = _tel_work;
		this._tel_fax = _tel_fax;
		this._email = _email;
		this._organisation = _organisation;
		this._url = _url;
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
		return _name +" "+ _upi;
		
	}

}
