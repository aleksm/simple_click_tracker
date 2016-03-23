package com.aleksm.simpleclicktracker.config;

import java.io.Serializable;

/**
* Settings provider 
* 
* <P> Provide configuration loaded from external file
*  
* @author aleksm
* @version 1.0.0
*/
public interface IAppSettingsProvider extends Serializable{
	
	/**
	* Provide configuration settings   
	* 
	* @return Properties contain configuration from external file
	*/
	BaseConfigurationProperties getAppSettings();
	
	/**
	* Load configuration from external file
	*/
	void loadConfiguration();
}
