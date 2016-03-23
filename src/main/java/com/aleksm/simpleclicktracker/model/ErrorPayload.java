package com.aleksm.simpleclicktracker.model;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.ReqResJson;

public class ErrorPayload extends PropertiesPayload{

	private static final long serialVersionUID = -1608636553907063015L;
	
	
	public ErrorPayload(String msg){
		super();
		addErrorPayload(msg);
	}
	
	private void addErrorPayload(String msg){
		SCTObject error = new SCTObject();
		error.putString(ReqResJson.Fields.status, Constants.ERROR);
		error.putString(ReqResJson.Fields.status_description, msg);
		addNewData(error);
	}

}
