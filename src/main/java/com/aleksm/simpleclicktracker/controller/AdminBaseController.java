package com.aleksm.simpleclicktracker.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.json.JSONFormatter;
import com.aleksm.simpleclicktracker.model.ErrorPayload;
import com.aleksm.simpleclicktracker.model.PropertiesPayload;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.RESTWSResponse;
import com.aleksm.simpleclicktracker.service.IControllerService;


/**
* Admin base Spring MVC controller  
* 
* <P> Base class for Admin API for campaign management. 
*  
* @author aleksm
* @version 1.0.0
*/
public abstract class AdminBaseController extends SCTBaseController {
	
	private static final Logger log = Logger.getLogger(AdminBaseController.class.getName());
	
	
	protected String getExecutionResult(RESTWSRequest wsRequest, IControllerService<?,?> service, ServiceContext context){
		
		log.info("Received request with id: "+wsRequest.getRequestId());
		log.info("JSON request: "+JSONFormatter.toJson(wsRequest));
		
		RESTWSResponse response = new RESTWSResponse();
		
		try{
			response.setInitialRequestId(wsRequest.getRequestId());
			
			service.execute(wsRequest, context);
			
			handleResult(service.getResult());
			
			prepareResponse(response);
			
		}catch(Exception ex){
			log.warning("Cannot prepare response. Error msg:"+ex.toString());
			response.setPayload(new ErrorPayload(ex.getMessage()));
		}
		
		// compose json response
		String json = response.toJson();
		try {
			byte [] bJsonUtf8 = json.getBytes(Constants.CHAR_UTF8);
			String jsonUtf8 = new String (bJsonUtf8, Charset.forName(Constants.CHAR_UTF8));
			
			log.info("Returning response for request with id: "+wsRequest.getRequestId());
			log.info("JSON response: "+JSONFormatter.toJson(response));
			
			return jsonUtf8;
		} catch (UnsupportedEncodingException e) {
			log.severe("Cannot compose json message. Error: "+e.getMessage());
		}
		
		return null;
	}
	
	private void prepareResponse(RESTWSResponse response){
		PropertiesPayload payload = getPayload();
		
		response.setPayload(payload);
	}
	
	
	protected abstract PropertiesPayload getPayload();
	
}
