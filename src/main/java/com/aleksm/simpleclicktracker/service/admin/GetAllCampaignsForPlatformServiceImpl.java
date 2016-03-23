package com.aleksm.simpleclicktracker.service.admin;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.CampaignObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.BaseService;

/**
 * Implementation of get all campaigns for platform service
 * 
 * Returns all campaigns from DB for given platform. 
 * 
 * @author aleksm
 * @version 1.0.0
 */
public class GetAllCampaignsForPlatformServiceImpl extends BaseService<Campaign, List<Campaign>> implements GetAllCampaignsForPlatformService{

	private static final Logger log = Logger.getLogger(GetAllCampaignsForPlatformServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			request = prepareRequest(wsRequest, context);
			
			CampaignObjectifyDao cod = new CampaignObjectifyDao();
			
			//get all campaigns
			List<Campaign>tmpList = cod.list();
			
			if (tmpList != null && tmpList.size() >0){
				result = new ArrayList<Campaign>();
				for (Campaign cmp : tmpList){
					if (cmp.getPlatformIds().containsAll(request.getPlatformIds()))
						result.add(cmp);
				}	
			}else{
				result = tmpList;
			}
			
			if (result==null)
				throw new WrongRequestException("No campaign found!");
			
			
		}catch (WrongRequestException reqException){
			Campaign cmp = new Campaign();
			cmp.setStatus(Constants.STATUS_ERROR);
			cmp.setStatusDesc(reqException.getMessage());
			result.add(cmp);
		}catch(Exception e){
			Campaign cmp = new Campaign();
			cmp.setStatus(Constants.STATUS_ERROR);
			cmp.setStatusDesc(e.toString());
			result.add(cmp);
		}
	}
	
	private Campaign prepareRequest(RESTWSRequest wsRequest, ServiceContext context) throws Exception{
		Campaign req = new Campaign();
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		if (sct.getString(ReqResJson.Fields.platformIds)==null ||
				sct.getString(ReqResJson.Fields.platformIds).isEmpty())
			throw new WrongRequestException("Platform not given!");
		
		String [] platformsArray = sct.getString(ReqResJson.Fields.platformIds).split(",");
		
		ArrayList<Long> platforms = new ArrayList<Long>();
		
		for (String platform:platformsArray){
			platform=platform.trim();
			
			if (DicPlatformType.Platform.unknown.ordinal() == getPlatformId(platform))
				throw new WrongRequestException("Unknown platform given!");
			platforms.add(getPlatformId(platform));
		}
		
		req.setPlatformIds(platforms);
		
		return req;
	}
	
}
