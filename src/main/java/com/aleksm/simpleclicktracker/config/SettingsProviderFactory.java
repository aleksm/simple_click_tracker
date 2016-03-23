package com.aleksm.simpleclicktracker.config;

/**
* Factory for settings provider 
* 
* <P> Ensure appropriate settings provider is used when reading configuration. 
*  
* @author aleksm
* @version 1.0.0
*/
public class SettingsProviderFactory {

	/**
	 * Factory method provides settings provider
	 * 
	 * @return Google App Engine settings provider since only GAE is supported at the moment
	 */
	public IAppSettingsProvider resolveProvider(){
		//for now just one provider - Google App Engine
		return new GAESettingsProvider();
	}
}
