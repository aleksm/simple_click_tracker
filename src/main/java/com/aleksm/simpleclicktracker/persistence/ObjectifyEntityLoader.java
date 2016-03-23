package com.aleksm.simpleclicktracker.persistence;

import com.aleksm.simpleclicktracker.persistence.entity.Campaign;
import com.aleksm.simpleclicktracker.persistence.entity.Click;
import com.googlecode.objectify.ObjectifyService;

/**
* Entity loader    
* 
* <P> Loads all existing entities at startup so they are ready whenever they need
* to be used.  
*  
* @author aleksm
* @version 1.0.0
*/
public class ObjectifyEntityLoader {
	
	public static void loadEntities(){
		
		ObjectifyService.register(Campaign.class);
		ObjectifyService.register(Click.class);
		
	}
}
