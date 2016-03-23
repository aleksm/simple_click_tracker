package com.aleksm.simpleclicktracker.common;

public class DicPlatformType {

	public enum Platform{
		unknown,
		Android,
		IPhone,
		WindowsPhone
	}
	
	public static Long getId(String name){
		return new Long(Platform.valueOf(name).ordinal());
	}
}
