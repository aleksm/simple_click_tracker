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
import com.aleksm.simpleclicktracker.service.admin.GetNumberOfClicksPerPlatformService;

@Controller
@RequestMapping("/")
@Scope("request")
public class GetNumberOfClicksPerPlatformControllerImpl extends AdminBaseController{

	private static final Logger log = Logger.getLogger(GetNumberOfClicksPerPlatformControllerImpl.class.getName());
	
	@Autowired
	GetNumberOfClicksPerPlatformService getNumberOfClicksPerPlatformService;
	
	@RequestMapping(value = URL.ADMIN_GET_CLICKS_PLATFORM, method = RequestMethod.POST, produces = Constants.APP_JSON_CHAR_UTF8, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public String getNumberOfClicksPerPlatform(@RequestBody final String json, BindingResult result) throws Exception{
		return super.getExecutionResult(json, getNumberOfClicksPerPlatformService);
	}
	
	@Override
	protected PropertiesPayload getPayload() {
		PropertiesPayload payload = new PropertiesPayload();
		
		Click result = (Click)getNumberOfClicksPerPlatformService.getResult();
		SCTObject sct = new SCTObject();
		
		if (result != null){
			log.info("step 2");	
			sct.putString(ReqResJson.Fields.status, new Integer(result.getStatus()).toString());
			
			if (result.getStatus() == Constants.STATUS_OK){
				log.info("step 3");
				
				sct.putString(ReqResJson.Fields.numberOfClicks, new Integer(result.getResultNumber()).toString());
				
				payload.addNewData(sct);
				
			}else{
				//error
				log.info("step 4");
				sct.putString(ReqResJson.Fields.status_description, "Cannot get number of clicks for selected platform.");
				payload.addNewData(sct);
			}
			
		}else{
			log.info("step 5");
			sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_ERROR).toString());
			sct.putString(ReqResJson.Fields.status_description, "Cannot get number of clicks for selected platform.");
			payload.addNewData(sct);
		}
		
		return payload;
	}

	@Override
	protected void handleResult(Object response) {
		//nothing
	}

}
