package com.aleksm.simpleclicktracker.config;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
* Base properties handler 
* 
* <P> Extends Properties class and adds support for easier manipulation with properties
*  
* @author aleksm
* @version 1.0.0
*/
public class BaseConfigurationProperties extends Properties{

	private static final long serialVersionUID = 9130072697909198509L;
	
	public String getProperty(String key, String defaultValue){
		String prop = super.getProperty(key);
		if (prop == null || prop.isEmpty())
			return defaultValue;
		return prop;
	}
	
	public int getIntProperty(String key, int defaultValue) {
		String prop = super.getProperty(key);
		if (prop == null || prop.isEmpty())
			return defaultValue;
		return Integer.valueOf(prop).intValue();
	}

	public String[] getPropertyArray(String key, String delimiter) {
		// get delimited property values
		String val = super.getProperty(key);
		if (val == null)
			return null;

		// tokenize the values
		Vector<String> vl = new Vector<String>();
		StringTokenizer st = new StringTokenizer(val, delimiter);
		while (st.hasMoreTokens())
			vl.add(st.nextToken());

		// return array of property values
		return (String[]) vl.toArray(new String[0]);
	}

	public boolean getBooleanProperty(String key, boolean defaultValue) {
		String prop = super.getProperty(key);
		if (prop == null || prop.isEmpty())
			return defaultValue;
		return Boolean.valueOf(prop).booleanValue();
	}

}
