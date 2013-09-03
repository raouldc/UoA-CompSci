package com.raouldc.uoacompsci;

public class Course {
	String _title,_code,_semester;
	
	public Course(String code, String semester, String title)
	{
		_code = code;
		_semester = semester;
		_title = title;
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

}
