package com.aleksm.simpleclicktracker.service.admin;


import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.ClickObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.persistence.entity.Click;
import com.aleksm.simpleclicktracker.service.BaseService;

/**
 * Implementation of get number of clicks for selected campaign and platform service
 * 
 * Returns number of registered clicks for given campaign and platform 
 * 
 * @author aleksm
 * @version 1.0.0
 */
public class GetNumberOfClicksPerCampaignPlatformServiceImpl extends BaseService<Click, Click> implements GetNumberOfClicksPerCampaignPlatformService{

	private static final Logger log = Logger.getLogger(GetNumberOfClicksPerCampaignPlatformServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			request = prepareRequest(wsRequest, context);
			
			ClickObjectifyDao clickod = new ClickObjectifyDao();
			
			int numberOfClicks = clickod.getNumberOfClicksForPlatformAndCampaign(request.getPlatform(), request.getCampaignId());
			
			result = request;
			
			result.setStatus(Constants.STATUS_OK);
			result.setResultNumber(numberOfClicks);
			
		}catch (WrongRequestException reqException){
			Click result = new Click();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(reqException.getMessage());
		}catch(Exception e){
			Campaign result = new Campaign();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(e.toString());
		}
	}
	
	private Click prepareRequest(RESTWSRequest wsRequest, ServiceContext context) throws Exception{
		Click req = new Click();
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		if (sct.getString(ReqResJson.Fields.platformId)==null ||
				sct.getString(ReqResJson.Fields.platformId).isEmpty())
			throw new WrongRequestException("Platform not given!");
		
		String platform = sct.getString(ReqResJson.Fields.platformId);
		
		if (DicPlatformType.Platform.unknown.ordinal() == getPlatformId(platform))
			throw new WrongRequestException("Unknown platform given!");
		
		req.setPlatform(getPlatformId(platform));
		
		if (sct.getString(ReqResJson.Fields.campaignId)==null ||
				sct.getString(ReqResJson.Fields.campaignId).isEmpty())
			throw new WrongRequestException("Campaign not given!");
		
		req.setCampaignId(sct.getString(ReqResJson.Fields.campaignId));
		
		return req;
	}
	
}
