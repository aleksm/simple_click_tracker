package com.aleksm.simpleclicktracker.service.admin;


import java.util.ArrayList;
import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.DatabaseException;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.CampaignObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.BaseService;


public class AddCampaignServiceImpl extends BaseService<Campaign, Campaign> implements AddCampaignService{

	private static final Logger log = Logger.getLogger(AddCampaignServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			request = prepareRequest(wsRequest, context);
			
			CampaignObjectifyDao cod = new CampaignObjectifyDao();
			
			//check if it is existing campaign 
			Campaign selectedCampaign = cod.getByKey(request.getId());
			
			if (selectedCampaign != null){
				throw new WrongRequestException("Campaign already exist!");
			}
			
			//insert new campaign
			cod.create(request);
			
			result = request;
			result.setStatus(Constants.STATUS_OK);
			
		}catch (DatabaseException dbException){
			result = new Campaign();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(dbException.getMessage());
		}catch (WrongRequestException reqException){
			result = new Campaign();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(reqException.getMessage());
		}catch(Exception e){
			result = new Campaign();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(e.getMessage());
		}
	}
	
	
	private Campaign prepareRequest(RESTWSRequest wsRequest, ServiceContext context) throws Exception{
		Campaign req = new Campaign();
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		
		
		req.setId(sct.getString(ReqResJson.Fields.campaignId));
		req.setRedirectURL(sct.getString(ReqResJson.Fields.redirectUrl));
		
		if (sct.getString(ReqResJson.Fields.platformIds)==null ||
				sct.getString(ReqResJson.Fields.platformIds).isEmpty())
			throw new WrongRequestException("Platform not given!");
		
		String [] platformsArray = sct.getString(ReqResJson.Fields.platformIds).split(",");
		
		ArrayList<Long> platforms = new ArrayList<Long>();
		
		for (String platform:platformsArray){
			platform=platform.trim();
			
			if (DicPlatformType.Platform.unknown.ordinal() == getPlatformId(platform)){
				throw new WrongRequestException("Unknown platform given!");
			}
			
			platforms.add(getPlatformId(platform));
		}
		
		req.setPlatformIds(platforms);
		
		return req;
	}
	
}
