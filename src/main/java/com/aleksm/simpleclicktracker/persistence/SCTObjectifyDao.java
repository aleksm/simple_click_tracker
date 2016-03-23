package com.aleksm.simpleclicktracker.persistence;

import com.aleksm.simpleclicktracker.exception.DatabaseException;
import com.googlecode.objectify.Key;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

/**
* CRUD support    
* 
* <P> CRUD support for entities used for persistence.  
*  
* @author aleksm
* @version 1.0.0
*/
public class SCTObjectifyDao<T> {
	
	private Class<T> klazz;

	protected SCTObjectifyDao(Class<T> klazz){
		this.klazz = klazz;
	}
	
	public void create(T t) {
	    ofy().save().entity(t).now();
	}

	
	public String createWithKey(T t) {
	    Key<T> key =  ofy().save().entity(t).now();
	    return key.getString();
	}

	
	public Long createWithID(T t) {
	    Key<T> key =  ofy().save().entity(t).now();
	    return key.getId();
	}

	
	public void update(Long id, T t) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T tnew = ofy().load().type(klazz).id(id).now();
	    ofy().save().entity(tnew).now();
	}

	
	public void update(String key, T t) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T tnew = ofy().load().type(klazz).id(key).now();
	    ofy().save().entity(tnew).now();

	}

	
	public T getById(Long id) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    return ofy().load().type(klazz).id(id).now();
	}

	
	public T getByKey(String key) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    return ofy().load().type(klazz).id(key).now();
	}

	
	public List<T> list() {
	    List<T> list = ofy().load().type(klazz).list();
	    return list;
	}

	
	public void delete(Long id) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T t = ofy().load().type(klazz).id(id).now();
	    if(t != null){
	        ofy().delete().entity(t).now();
	    }
	}


	public void deleteByKey(String key) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T t = ofy().load().type(klazz).id(key).now();
	    if(t != null){
	        ofy().delete().entity(t).now();
	    }
	}

}
