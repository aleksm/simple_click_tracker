package com.aleksm.simpleclicktracker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

/**
* Google App Engine settings provider 
* 
* <P> Provide configuration loaded from external file. This implementation is adapted to 
* Google App Engine.
*  
* @author aleksm
* @version 1.0.0
*/
public class GAESettingsProvider implements IAppSettingsProvider{

	private static final long serialVersionUID = 8634433209836804529L;
	
	private BaseConfigurationProperties _properties;
	
	@Override
	public BaseConfigurationProperties getAppSettings() {
		return _properties;
	}

	/**
	 * Load properties from external properties file
	 * 
	 */
	@Override
	public void loadConfiguration() {
		_properties = new BaseConfigurationProperties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("SimpleClickTrackerProperties.xml");
		try {
			_properties.loadFromXML(is);
		} catch (InvalidPropertiesFormatException e) {
			//TODO
			e.printStackTrace();
		} catch (IOException e) {
			//TODO handle exception
			e.printStackTrace();
		} 
	}
	
	

}
