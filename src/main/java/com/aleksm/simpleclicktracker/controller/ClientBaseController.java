package com.aleksm.simpleclicktracker.controller;

import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.config.SCTConfiguration;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.json.JSONFormatter;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.service.IControllerService;

/**
* Client base Spring MVC controller  
* 
* <P> Base class for Client API for tracking clicks 
*  
* @author aleksm
* @version 1.0.0
*/
public abstract class ClientBaseController extends SCTBaseController{

	private static final Logger log = Logger.getLogger(ClientBaseController.class.getName());
	
	protected String getExecutionResult(RESTWSRequest wsRequest, IControllerService<?,?> service, ServiceContext context){
		
		log.info("JSON request: "+JSONFormatter.toJson(wsRequest));
		
		if (checkRequestValidity(wsRequest, context)){
			service.execute(wsRequest, context);
			
			return getResponseFromResult(wsRequest, context);
			
		}else{
			//redirect to default URL
			return getDefaultUrl();
		}
	}
	
	protected abstract boolean checkRequestValidity(RESTWSRequest wsRequest, ServiceContext context);
	
	protected abstract String getResponseFromResult(RESTWSRequest wsRequest, ServiceContext context);
	
	protected String getDefaultUrl(){
		return SCTConfiguration.getInstance().getDefaultRedirectURL();
	}
}
