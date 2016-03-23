package com.aleksm.simpleclicktracker.common;

/**
* REST Urls 
* 
* <P>Rest services endpoints for admin and user URLs 
*  
* @author aleksm
* @version 1.0.0
*/
public class URL {

	public static final String ADMIN_ADD_CAMPAIGN = "/admin/addCampaign";
	public static final String ADMIN_GET_CAMPAIGN = "/admin/getCampaign";
	public static final String ADMIN_UPDATE_CAMPAIGN = "/admin/updateCampaign";
	public static final String ADMIN_DELETE_CAMPAIGN = "/admin/deleteCampaign";
	public static final String ADMIN_GET_ALL_CAMPAIGNS = "/admin/getAllCampaigns";
	public static final String ADMIN_GET_ALL_CAMPAIGNS_FOR_PLATFORM = "/admin/getAllCampaignsForPlatform";
	public static final String ADMIN_GET_CLICKS_PLATFORM_CAMPAIGN = "/admin/getNumberOfClicksPerCampaignPlatform";
	public static final String ADMIN_GET_CLICKS_PLATFORM = "/admin/getNumberOfClicksPerPlatform";
	public static final String TRACKING_CLICK = "/track/trackClick";
	
}
