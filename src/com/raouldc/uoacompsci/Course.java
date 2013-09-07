package com.raouldc.uoacompsci;

import java.io.Serializable;

public class Course implements Serializable{
	String _title,_code,_semester;

	public void set_title(String _title) {
		this._title = _title;
	}

	public void set_code(String _code) {
		this._code = _code;
	}

	public void set_semester(String _semester) {
		this._semester = _semester;
	}

	public String get_title() {
		return _title;
	}

	public String get_code() {
		return _code;
	}

	public String get_semester() {
		return _semester;
	}
	
	@Override
	public String toString()
	{
		return _code + " : "+_title;
		
	}

}
