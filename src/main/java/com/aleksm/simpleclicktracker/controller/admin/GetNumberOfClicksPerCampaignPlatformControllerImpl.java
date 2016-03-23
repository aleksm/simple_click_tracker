package com.aleksm.simpleclicktracker.controller.admin;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.common.URL;
import com.aleksm.simpleclicktracker.controller.AdminBaseController;
import com.aleksm.simpleclicktracker.model.PropertiesPayload;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.entity.Click;
import com.aleksm.simpleclicktracker.service.admin.GetNumberOfClicksPerCampaignPlatformService;

@Controller
@RequestMapping("/")
@Scope("request")
public class GetNumberOfClicksPerCampaignPlatformControllerImpl extends AdminBaseController{

	private static final Logger log = Logger.getLogger(GetNumberOfClicksPerCampaignPlatformControllerImpl.class.getName());
	
	@Autowired
	GetNumberOfClicksPerCampaignPlatformService getNumberOfClicksPerCampaignPlatformService;
	
	@RequestMapping(value = URL.ADMIN_GET_CLICKS_PLATFORM_CAMPAIGN, method = RequestMethod.POST, produces = Constants.APP_JSON_CHAR_UTF8, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public String getNumberOfClicksPerPlatformAndCampaign(@RequestBody final String json, BindingResult result) throws Exception{
		return super.getExecutionResult(json, getNumberOfClicksPerCampaignPlatformService);
	}
	
	@Override
	protected PropertiesPayload getPayload() {
		PropertiesPayload payload = new PropertiesPayload();
		
		Click result = (Click)getNumberOfClicksPerCampaignPlatformService.getResult();
		SCTObject sct = new SCTObject();
		
		if (result != null){
			
			sct.putString(ReqResJson.Fields.status, new Integer(result.getStatus()).toString());
			
			if (result.getStatus() == Constants.STATUS_OK){

				
				sct.putString(ReqResJson.Fields.numberOfClicks, new Integer(result.getResultNumber()).toString());
				
				payload.addNewData(sct);
				
			}else{
				//error
				sct.putString(ReqResJson.Fields.status_description, "Cannot get number of clicks for selected platform and campaign.");
				payload.addNewData(sct);
			}
			
		}else{
			sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_ERROR).toString());
			sct.putString(ReqResJson.Fields.status_description, "Cannot get number of clicks for selected platform and campaign.");
			payload.addNewData(sct);
		}
		
		return payload;
	}

	@Override
	protected void handleResult(Object response) {
		//nothing
	}

}
