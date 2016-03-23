package com.aleksm.simpleclicktracker.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;
import com.aleksm.simpleclicktracker.context.ServiceContext;
import com.aleksm.simpleclicktracker.model.RESTWSRequest;
import com.aleksm.simpleclicktracker.service.IControllerService;

/**
* Base Spring MVC controller 
* 
* <P> Base controller class for both APIs (Admin and client). Resolves platform type based on request. Maps
* every REST WS call into appropriate service, which further communicates with Java Persistence layer and 
* provides information needed for admin and client APIs.
*  
* @author aleksm
* @version 1.0.0
*/
public abstract class SCTBaseController {
	
	protected String getExecutionResult(final String json, IControllerService<?,?> service){
		return this.getExecutionResult(RESTWSRequest.fromJson(json), service, getContext());
	}
	
	protected String getExecutionResult(IControllerService<?,?> service){
		return this.getExecutionResult(new RESTWSRequest(), service, getContext());
	}
	
	protected abstract String getExecutionResult(RESTWSRequest wsRequest, IControllerService<?,?> service, ServiceContext context);
	
	private String getRemoteAddr(){
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
	}
	
	protected String getPlatformType(String platform){
		
		if (Constants.PLATFORM_TYPE_ANDROID.equals(platform)){
			return DicPlatformType.Platform.Android.name();
		}else if(Constants.PLATFORM_TYPE_IOS.equals(platform)){
			return DicPlatformType.Platform.IPhone.name();
		}else if(Constants.PLATFORM_TYPE_WIN.equals(platform)){
			return DicPlatformType.Platform.WindowsPhone.name();
		}
		
		return DicPlatformType.Platform.unknown.name();
	}
	
	protected Long getPlatformId(String platform){
		String platformName = getPlatformType(platform);
		
		return DicPlatformType.getId(platformName);
	}
	
	protected String getPlatform(Long platformId){
		if (DicPlatformType.Platform.Android.ordinal() == platformId){
			return Constants.PLATFORM_TYPE_ANDROID;
		}else if(DicPlatformType.Platform.IPhone.ordinal() == platformId){
			return Constants.PLATFORM_TYPE_IOS;
		}else if(DicPlatformType.Platform.WindowsPhone.ordinal() == platformId){
			return Constants.PLATFORM_TYPE_WIN;
		}
		return null;
	}
	
	protected ServiceContext getContext(){
		ServiceContext context = new ServiceContext();
		
		context.setRemoteAddr(getRemoteAddr());
		
		return context;
	}
	
	protected String getAuthString(){
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getHeader(Constants.REQ_HEADER_AUTHORIZATION);
	}
	
	protected abstract void handleResult(Object response);
}
