package com.aleksm.simpleclicktracker.config;

import com.aleksm.simpleclicktracker.persistence.ObjectifyEntityLoader;

/**
* Simple Click Tracker configuration 
* 
* <P> Initialize application settings by loading its configuration from external 
* configuration file. Prepare Java persistence and load entities.
*  
* @author aleksm
* @version 1.0.0
*/
public class SCTConfiguration {

	private static SCTConfiguration _configInstance = null;
	
	private IAppSettingsProvider _settingsProvider;
	
	private BaseConfigurationProperties _appSettings;
	
	/**
	 * Singleton method for providing app  configuration 
	 * 
	 * @return Configuration 
	 */
	public static SCTConfiguration getInstance(){
		if (_configInstance == null){
			_configInstance = new SCTConfiguration();
		}
		return _configInstance;
	}

	private SCTConfiguration(){
		//For this purpose only Google App Engine settings provider
		_settingsProvider = new SettingsProviderFactory().resolveProvider();
		
		_settingsProvider.loadConfiguration();
		_appSettings = _settingsProvider.getAppSettings();
		
		//initialize db
		ObjectifyEntityLoader.loadEntities();
	}
	
	public BaseConfigurationProperties getAppSettings(){
		return _appSettings;
	}
	
	/**
	 * Deafult redirect URL 
	 * 
	 * @return Default URL used for redirection. Can be easily changed in configuration file.  
	 */
	public String getDefaultRedirectURL(){
		//TODO implementation
		return _appSettings.getProperty("default.redirect.url");
	}
	
	/**
	 * Deafult date format  
	 * 
	 * @return Default date representation.  
	 */
	public String getDateFormat(){
		return _appSettings.getProperty("date.format");
	}
	
	
}
