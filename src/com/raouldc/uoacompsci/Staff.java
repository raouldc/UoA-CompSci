package com.raouldc.uoacompsci;

public class Staff {

	private String _name, _email;

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	@Override
	public String toString()
	{
		return _name;
		
	}

}
