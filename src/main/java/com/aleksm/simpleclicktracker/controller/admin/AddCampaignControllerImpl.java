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
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.admin.AddCampaignService;

@Controller
@RequestMapping("/")
@Scope("request")
public class AddCampaignControllerImpl extends AdminBaseController{

	private static final Logger log = Logger.getLogger(AddCampaignControllerImpl.class.getName());
	
	@Autowired
	AddCampaignService addCampaignService;
	
	@RequestMapping(value = URL.ADMIN_ADD_CAMPAIGN, method = RequestMethod.POST, produces = Constants.APP_JSON_CHAR_UTF8, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public String add(@RequestBody final String json, BindingResult result) throws Exception{
		return super.getExecutionResult(json, addCampaignService);
	}
	
	@Override
	protected PropertiesPayload getPayload() {
		PropertiesPayload payload = new PropertiesPayload();
		
		Campaign result = (Campaign)addCampaignService.getResult();
		SCTObject sct = new SCTObject();
		
		if (result != null){
			
			sct.putString(ReqResJson.Fields.status, new Integer(result.getStatus()).toString());
				
			if (result.getStatus() != Constants.STATUS_OK){
				sct.putString(ReqResJson.Fields.status_description, result.getStatusDesc());
			}
			
		}else{
			sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_ERROR).toString());
			sct.putString(ReqResJson.Fields.status_description, "Cannot add new campaign.");
		}
		
		payload.addNewData(sct);
		return payload;
	}

	@Override
	protected void handleResult(Object response) {
		//nothing
	}

}
