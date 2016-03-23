package com.aleksm.simpleclicktracker.service;

import com.aleksm.simpleclicktracker.context.ServiceContext;

import com.aleksm.simpleclicktracker.model.RESTWSRequest;

/**
 * Controller service interface
 * 
 * Service invoked by spring MVC controller. Executes various queries against DB (Objectify)
 * and returns result to controller   
 * 
 * @author aleksm
 * @version 1.0.0
 *
 * @param <T> - Request message that extends {@link}DBRequest or {@link}DBResponse object
 * @param <K> - Response message extends {@link}DBResponse object
 */
public interface IControllerService<T , K> {
	
	/**
	 * Returns response object to controller
	 * 
	 * @return Result object that contains result of a DB query
	 */
	public Object getResult();

	/**
	 * Executes scenario that usually includes some query on DB (Java persistence) and 
	 * prepares response object for controller
	 * 
	 * @param wsRequest - json request received by controller 
	 * @param context - service context
	 */
	public void execute(RESTWSRequest wsRequest, ServiceContext context);
}
