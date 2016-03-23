package com.aleksm.simpleclicktracker.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PropertiesPayload implements Serializable{

	private static final long serialVersionUID = -2903789338014829634L;
	
	private List<SCTObject> data = new LinkedList<SCTObject>();
	
	public List<SCTObject> getData(){
		return data;
	}
	
	public void setData(List<SCTObject> data){
		this.data = data;
	}
	
	public void addNewData(SCTObject object){
		this.data.add(object);
	}
}
