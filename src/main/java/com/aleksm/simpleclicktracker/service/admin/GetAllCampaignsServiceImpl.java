package com.aleksm.simpleclicktracker.service.admin;


import java.util.List;
import java.util.logging.Logger;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.exception.WrongRequestException;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.persistence.CampaignObjectifyDao;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.BaseService;

/**
 * Implementation of get all campaigns service
 * 
 * Returns all existing campaigns from DB. 
 * 
 * @author aleksm
 * @version 1.0.0
 */
public class GetAllCampaignsServiceImpl extends BaseService<Campaign, List<Campaign>> implements GetAllCampaignsService{

	private static final Logger log = Logger.getLogger(GetAllCampaignsServiceImpl.class.getName());
	
	@Override
	public void execute(RESTWSRequest wsRequest, ServiceContext context) {
		try {
			CampaignObjectifyDao cod = new CampaignObjectifyDao();

			//get campaign
			result = cod.list();
			
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
	
	
}
