package com.raouldc.uoacompsci;

public class UnencryptedStaff implements Staff {
	
	private String _name, _email;
	
	public UnencryptedStaff(String name, String email)
	{
		_name = name;
		_email = email;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return _name;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return _email;
	}

}
