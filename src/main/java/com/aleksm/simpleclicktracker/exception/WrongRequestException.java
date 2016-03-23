package com.aleksm.simpleclicktracker.exception;

public class WrongRequestException extends Exception{

	private static final long serialVersionUID = 1L;

	public WrongRequestException(String string) {
		super(string);
	}
}
