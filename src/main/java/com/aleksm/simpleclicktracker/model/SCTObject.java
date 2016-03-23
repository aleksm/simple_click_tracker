package com.aleksm.simpleclicktracker.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
* Simple Click Tracker object   
* 
* <P> JSON properties in request and response messages 
*  
*  
* @author aleksm
* @version 1.0.0
*/
public class SCTObject implements Serializable{

	private static final long serialVersionUID = 4447653265999188127L;
	
	private LinkedHashMap<String, Object> properties = new LinkedHashMap<String,Object>();
	
	public SCTObject(){
		
	}
	
	public void putString(Enum<?> key, String value){
		putString (key.name(), value);
	}
	
	public void putString(String key, String value){
		properties.put(key, value);
	}
	
	public String getString(Enum<?> key){
		return getString(key.name());
	}
	
	public String getString(String key){
		String value = (String)properties.get(key);
		return value;
	}

}
