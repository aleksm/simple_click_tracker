package com.aleksm.simpleclicktracker.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* JSON transformations  
* 
* <P> Convert JSON to String and vice versa using GSON library. 
*  
* @author aleksm
* @version 1.0.0
*/
public class JSONFormatter {

	private static Gson createGSON(Class<?> klass){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder
				.setPrettyPrinting()
				.create();
		return gson;
	}
	
	public static <T> String toJson(T object){
		Gson gson = createGSON(object.getClass());
		String json = gson.toJson(object);
		return json;
	}
	
	public static <T> T fromJson(String json, Class<T> klass){
		Gson gson = createGSON(klass);
		return gson.fromJson(json, klass);
	}
}
