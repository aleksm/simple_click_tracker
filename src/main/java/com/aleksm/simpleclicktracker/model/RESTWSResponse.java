package com.aleksm.simpleclicktracker.model;

import com.aleksm.simpleclicktracker.json.JSONFormatter;

public class RESTWSResponse extends RESTWSBaseObject{

	private static final long serialVersionUID = -7000029842798013939L;
	
	
	private String initialRequestId;  

	public RESTWSResponse fromJson(String json){
		return JSONFormatter.fromJson(json, RESTWSResponse.class);
	}

	public String getInitialRequestId() {
		return initialRequestId;
	}

	public void setInitialRequestId(String initialRequestId) {
		this.initialRequestId = initialRequestId;
	}
	
	
}
