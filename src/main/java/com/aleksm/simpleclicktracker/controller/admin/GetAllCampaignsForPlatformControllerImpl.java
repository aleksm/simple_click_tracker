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
import com.aleksm.simpleclicktracker.service.admin.GetAllCampaignsForPlatformService;

@Controller
@RequestMapping("/")
@Scope("request")
public class GetAllCampaignsForPlatformControllerImpl extends AdminBaseController{

	private static final Logger log = Logger.getLogger(GetAllCampaignsForPlatformControllerImpl.class.getName());
	
	@Autowired
	GetAllCampaignsForPlatformService getAllCampaignsForPlatformService;
	
	@RequestMapping(value = URL.ADMIN_GET_ALL_CAMPAIGNS_FOR_PLATFORM, method = RequestMethod.POST, produces = Constants.APP_JSON_CHAR_UTF8, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public String get(@RequestBody final String json, BindingResult result) throws Exception{
		return super.getExecutionResult(json, getAllCampaignsForPlatformService);
	}
	
	@Override
	protected PropertiesPayload getPayload() {
		PropertiesPayload payload = new PropertiesPayload();
	
		List<Campaign> result = (List<Campaign>)getAllCampaignsForPlatformService.getResult();
		SCTObject sct = new SCTObject();
		
		if (result != null){
			
			if (result.size() > 0){
				
				sct.putString(ReqResJson.Fields.status, new Integer(result.get(0).getStatus()).toString());
				
				if (result.get(0).getStatus() != Constants.STATUS_OK){
					
					sct.putString(ReqResJson.Fields.status_description, result.get(0).getStatusDesc());
				}
				
				payload.addNewData(sct);
				
				final String datePattern = SCTConfiguration.getInstance().getDateFormat();
				DateFormat sdf = new SimpleDateFormat(datePattern);
				
				for (Campaign cmp:result){
					SCTObject sct1 = new SCTObject();
					sct1.putString(ReqResJson.Fields.campaignId, cmp.getId().toString());
					List<Long> platforms = cmp.getPlatformIds();
					StringBuffer sb = new StringBuffer();
					for (Long platform:platforms){
						if (sb.length()==0)
							sb.append(getPlatform(platform));
						else
							sb.append(","+getPlatform(platform));	
					}
					sct1.putString(ReqResJson.Fields.platformIds, sb.toString());
					sct1.putString(ReqResJson.Fields.redirectUrl, cmp.getRedirectURL());
					
					sct1.putString(ReqResJson.Fields.createDate, sdf.format(cmp.getCreateDate()));
					sct1.putString(ReqResJson.Fields.updateDate, sdf.format(cmp.getLastUpdate()));
					payload.addNewData(sct1);
				}
			}else{
				//empty list
				sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_NO_RECORD).toString());
				sct.putString(ReqResJson.Fields.status_description, "There is no campaign for given criteria stored in database!");
				payload.addNewData(sct);
			}
			
		}else{
			sct.putString(ReqResJson.Fields.status, new Integer(Constants.STATUS_ERROR).toString());
			sct.putString(ReqResJson.Fields.status_description, "Cannot get the list of campaigns for sekected criteria.");
			payload.addNewData(sct);
		}
		
		return payload;
	}

	@Override
	protected void handleResult(Object response) {
		//nothing
	}

}
