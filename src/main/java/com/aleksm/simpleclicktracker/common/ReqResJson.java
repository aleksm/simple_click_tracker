package com.aleksm.simpleclicktracker.common;

/**
* Json and entities properties names  
* 
* <P> Enum fields that represent json properties in both request and response, as well as
* properties of entities used for persistence   
*  
* @author aleksm
* @version 1.0.0
*/
public class ReqResJson {
	
	public enum Fields{
		status,
		status_description, 
		
		/*fields for add campaign service*/
		campaignId, 
		redirectUrl, 
		platformIds, 
		createDate, 
		updateDate, 
		platformId, 
		numberOfClicks, 
		initialRequestId
		
		
	}

}
