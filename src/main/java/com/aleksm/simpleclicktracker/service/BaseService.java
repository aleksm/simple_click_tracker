package com.aleksm.simpleclicktracker.service;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.common.DicPlatformType;

/**
 * Base service class 
 * 
 * Base class that implements returning of result object. Implementation of execute method 
 * is supposed to be done in derived classes that represent each service. 
 * 
 * @author aleksm
 * @version 1.0.0
 *
 * @param <T> - Request message that extends {@link}DBRequest or {@link}DBResponse object
 * @param <K> - Response message extends {@link}DBResponse object
 */
public class BaseService<T, K> {

	protected T request;
	protected K result;
	
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
		return DicPlatformType.getId(getPlatformType(platform));
	}
	
	
	public K getResult() {
		return result;
	}
}
