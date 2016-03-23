package com.aleksm.simpleclicktracker.persistence;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.aleksm.simpleclicktracker.persistence.entity.Click;

/**
* Click support     
* 
* <P> CRUD support for Click entity, as well as simple implementation of count DB operation.   
*  
* @author aleksm
* @version 1.0.0
*/
public class ClickObjectifyDao extends SCTObjectifyDao<Click>{
	
	
	protected ClickObjectifyDao(Class<Click> klazz) {
		super(klazz);
		// TODO Auto-generated constructor stub
	}

	public ClickObjectifyDao(){
		super(Click.class);
	}
	
	public int getNumberOfClicksForPlatform(Long platformId){
		 List<Click> list = ofy().load().type(Click.class).filter("platform", platformId).list();
		 if (list != null)
			 return list.size();
		 return 0;
	}
	
	public int getNumberOfClicksForPlatformAndCampaign(Long platformId, String campaignId){
		 List<Click> list = ofy().load().type(Click.class).filter("platform", platformId).filter("campaignId", campaignId).list();
		 if (list != null)
			 return list.size();
		 return 0;
	}
}
