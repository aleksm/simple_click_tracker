package com.aleksm.simpleclicktracker.model;

import java.io.Serializable;
import com.aleksm.simpleclicktracker.json.JSONFormatter;

/**
* Base REST object    
* 
* <P> Base object responsible for mapping REST WS requests into objects recognizable and used by services for
* DB queries, as well as for mapping DB queries results into REST WS response messages.  
*  
* @author aleksm
* @version 1.0.0
*/
public class RESTWSBaseObject implements Serializable{

	private static final long serialVersionUID = 7535988550976128181L;
	
	protected PropertiesPayload payload = new PropertiesPayload();
	
	public PropertiesPayload getPayload(){
		return payload;
	}
	
	public void setPayload (PropertiesPayload payload){
		this.payload = payload;
	}
	
	public SCTObject getFirstSCTObject(){
		return getFirstSCTObject(payload);
	}

	public SCTObject getFirstSCTObject(PropertiesPayload payload){
		try{
			return payload.getData().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	public String toJson(){
		return JSONFormatter.toJson(this);
	}
}
