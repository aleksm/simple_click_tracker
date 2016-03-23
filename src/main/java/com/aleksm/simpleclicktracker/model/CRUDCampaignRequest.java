package com.aleksm.simpleclicktracker.model;

import java.util.List;

/**
* Request object for campaign management
* 
* <P> This object is used in all activities involve working with campaigns stored 
* in DB (Objectify persistence). 
*  
* @author aleksm
* @version 1.0.0
*/
public class CRUDCampaignRequest extends DBRequest{

	private String id;
	private String redirectUrl;
	private List<Long> platformIds;
	
	public CRUDCampaignRequest(){	
	}
	
	public CRUDCampaignRequest(String id, String redirectUrl, List<Long> platformIds){
		this.id = id;
		this.redirectUrl = redirectUrl;
		this.platformIds = platformIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public List<Long> getPlatformIds() {
		return platformIds;
	}

	public void setPlatformIds(List<Long> platformIds) {
		this.platformIds = platformIds;
	}
	
	
}
