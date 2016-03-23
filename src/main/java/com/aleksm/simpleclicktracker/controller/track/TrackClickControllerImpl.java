package com.aleksm.simpleclicktracker.controller.track;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.common.ReqResJson;
import com.aleksm.simpleclicktracker.common.URL;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.controller.ClientBaseController;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.model.SCTObject;
import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.persistence.entity.Click;
import com.aleksm.simpleclicktracker.service.admin.GetCampaignService;
import com.aleksm.simpleclicktracker.service.track.AddClickService;


@Controller
@RequestMapping("/")
@Scope("request")
public class TrackClickControllerImpl extends ClientBaseController{

	
	private static final Logger log = Logger.getLogger(TrackClickControllerImpl.class.getName());
	
	@Autowired
	AddClickService addClickService;
	
	@Autowired
	GetCampaignService getCampaignService;
	
	@RequestMapping(value = URL.TRACKING_CLICK, method = RequestMethod.POST, consumes = Constants.APP_JSON_CHAR_UTF8)
	@ResponseBody
	public RedirectView processClick(final HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody final String json, BindingResult result) throws Exception{
		String redirectUrl = super.getExecutionResult(json, getCampaignService);
		
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl(redirectUrl);
	    return redirectView;
	}
	
	
	@Override
	protected boolean checkRequestValidity(RESTWSRequest wsRequest, ServiceContext context) {
		
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		if (sct!=null){
		
			try{
				String campaignId = sct.getString(ReqResJson.Fields.campaignId);
				
				String platformId = sct.getString(ReqResJson.Fields.platformId);
				
				
				if (campaignId == null || platformId == null || campaignId.isEmpty() || platformId.isEmpty()){
					log.warning("Missing campaignId or platformId");
					return false;					
				}
				
				return true;
			}catch(Exception e){
				log.severe("Error while getting campaingId and platformId from request.");
				return false;
			}
		}
		log.severe("Unexpected request received");
		return false;
	}
	

	@Override
	protected String getResponseFromResult(RESTWSRequest wsRequest, ServiceContext context) {
		SCTObject sct = wsRequest.getFirstSCTObject();
		
		Campaign campaign = (Campaign)getCampaignService.getResult();
		if (campaign==null || campaign.getStatus()!=Constants.STATUS_OK){
			log.severe("Could not get information about campaign.");
			return getDefaultUrl();
		}
		
		try{
			
			String platform = sct.getString(ReqResJson.Fields.platformId);
			
			if (DicPlatformType.Platform.unknown.ordinal() == getPlatformId(platform)){
				log.severe("Unknown platform given.");
				return getDefaultUrl();
			}
			
			//all good, return redirect url
			if (campaign.getPlatformIds().contains(getPlatformId(platform))){
				log.info("All good, user will be redirected to: "+campaign.getRedirectURL());
				insertClick(wsRequest, context);
				return campaign.getRedirectURL();
			}

		}catch(Exception e){
			log.severe("Errow while getting campaign information.");
		}
		return getDefaultUrl();
	}

	private void insertClick(RESTWSRequest wsRequest, ServiceContext context){
		addClickService.execute(wsRequest, context);
		
		Click click = (Click)addClickService.getResult();
		if (click.getStatus()!=Constants.STATUS_OK){
			
			log.warning("Error while trying to save click information!");
		}else{
			//everything is OK
			log.info("Click information successfully stored");
		}	
	}
	
	@Override
	protected void handleResult(Object response) {
		//nothing here
	}

}
