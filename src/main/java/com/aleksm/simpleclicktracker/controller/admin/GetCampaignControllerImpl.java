package com.aleksm.simpleclicktracker.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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
import com.aleksm.simpleclicktracker.config.SCTConfiguration;
import com.aleksm.simpleclicktracker.controller.AdminBaseController;
import com.aleksm.simpleclicktracker.model.PropertiesPayload;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.service.admin.GetCampaignService;

@Controller
@RequestMapping("/")
@Scope("request")
public class GetCampaignControllerImpl extends AdminBaseController{

	private static final Logger log = Logger.getLogger(GetCampaignControllerImpl.class.getName());
	
	@Autowired
	GetCampaignService getCampaignService;
	
	@RequestMapping(value = URL.ADMIN_GET_CAMPAIGN, method = RequestMethod.POST, produces = Constants.APP_JSON_CHAR_UTF8, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public String get(@RequestBody final String json, BindingResult result) throws Exception{
		return super.getExecutionResult(json, getCampaignService);
	}
	
	@Override
	protected PropertiesPayload getPayload() {
		PropertiesPayload payload = new PropertiesPayload();
		
		Campaign result = (Campaign)getCampaignService.getResult();
		SCTObject sct = new SCTObject();
		
		if (result != null){
			
			final String datePattern = SCTConfiguration.getInstance().getDateFormat();
			DateFormat sdf = new SimpleDateFormat(datePattern);
			
			sct.putString(ReqResJson.Fields.status, new Integer(result.getStatus()).toString());
				
			if (result.getStatus() != Constants.STATUS_OK){
				sct.putString(ReqResJson.Fields.status_description, result.getStatusDesc());
			}else {
				//OK
				sct.putString(ReqResJson.Fields.campaignId, result.getId().toString());
				List<Long> platforms = result.getPlatformIds();
				StringBuffer sb = new StringBuffer();
				for (Long platform:platforms){
					if (sb.length()==0)
						sb.append(getPlatform(platform));
					else
						sb.append(","+getPlatform(platform));	
				}
				sct.putString(ReqResJson.Fields.platformIds, sb.toString());
				sct.putString(ReqResJson.Fields.redirectUrl, result.getRedirectURL());
				sct.putString(ReqResJson.Fields.createDate, sdf.format(result.getCreateDate()));
				sct.putString(ReqResJson.Fields.updateDate, sdf.format(result.getLastUpdate()));
			}
			
		}else{
			sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_ERROR).toString());
			sct.putString(ReqResJson.Fields.status_description, "Cannot get campaign.");
		}
		
		payload.addNewData(sct);
		return payload;
	}

	@Override
	protected void handleResult(Object response) {
		//nothing
	}

}
