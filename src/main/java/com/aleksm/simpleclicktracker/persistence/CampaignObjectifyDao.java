package com.aleksm.simpleclicktracker.persistence;

import com.aleksm.simpleclicktracker.persistence.entity.Campaign;

/**
* Campaign support    
* 
* <P> CRUD support for Campaign entity.  
*  
* @author aleksm
* @version 1.0.0
*/
public class CampaignObjectifyDao extends SCTObjectifyDao<Campaign>{

	protected CampaignObjectifyDao(Class<Campaign> klazz) {
		super(klazz);
	}
	
	public CampaignObjectifyDao(){
		super(Campaign.class);
	}

	
	
	
	
	
}
