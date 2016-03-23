package com.aleksm.simpleclicktracker.model;

import com.aleksm.simpleclicktracker.json.JSONFormatter;

public class RESTWSRequest extends RESTWSBaseObject{

	private static final long serialVersionUID = 2797604755622278846L;

	private String requestId;
	
	public static RESTWSRequest fromJson(String json){
		return JSONFormatter.fromJson(json, RESTWSRequest.class);
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}
