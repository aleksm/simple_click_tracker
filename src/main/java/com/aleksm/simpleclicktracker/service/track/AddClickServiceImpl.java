package com.aleksm.simpleclicktracker.service.track;


import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.DatabaseException;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.ClickObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Click;
import com.aleksm.simpleclicktracker.service.BaseService;

/**
 * Implementation of add click service
 * 
 * Inserts click information in DB 
 * 
 * @author aleksm
 * @version 1.0.0
 */
public class AddClickServiceImpl extends BaseService<Click, Click> implements AddClickService{

	private static final Logger log = Logger.getLogger(AddClickServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			request = prepareRequest(wsRequest, context);
			
			ClickObjectifyDao cod = new ClickObjectifyDao();
			
			//check if it is existing click 
			Click selectedClick = cod.getById(request.getId());
			
			if (selectedClick != null){
				throw new WrongRequestException("Click already exist!");
			}
			
			//insert new click
			cod.create(request);
			
			result = request;
			result.setStatus(Constants.STATUS_OK);
			
		}catch (DatabaseException dbException){
			result = new Click();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(dbException.getMessage());
		}catch (WrongRequestException reqException){
			result = new Click();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(reqException.getMessage());
		}catch(Exception e){
			result = new Click();
			result.setStatus(Constants.STATUS_ERROR);
			result.setStatusDesc(e.getMessage());
		}
	}
	
	
	private Click prepareRequest(RESTWSRequest wsRequest, ServiceContext context) throws Exception{
		Click req = new Click();
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		req.setId(System.currentTimeMillis());
		
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
