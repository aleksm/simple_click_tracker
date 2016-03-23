package com.aleksm.simpleclicktracker.service.admin;

import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.DatabaseException;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.CampaignObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.BaseService;

/**
 * Implementation of get campaign service
 * 
 * Checks if given campaign (with given ID) exist and if does returns its data from DB. 
 * 
 * @author aleksm
 * @version 1.0.0
 */
public class GetCampaignServiceImpl extends BaseService<Campaign, Campaign> implements GetCampaignService{

	private static final Logger log = Logger.getLogger(GetCampaignServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			request = prepareRequest(wsRequest, context);
			
			CampaignObjectifyDao cod = new CampaignObjectifyDao();

			//get campaign
			result = cod.getByKey(request.getId());
			
			if (result==null)
				throw new WrongRequestException("Campaign with id: "+ request.getId() +" doesn't exist!");
			
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
			result.setStatusDesc(e.toString());
		}
	}
	
	
	private Campaign prepareRequest(RESTWSRequest wsRequest, ServiceContext context) throws Exception{
		Campaign req = new Campaign();
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		req.setId(sct.getString(ReqResJson.Fields.campaignId));
		
		return req;
	}
	
}
