package com.aleksm.simpleclicktracker.persistence.entity;

import java.util.Date;
import java.util.List;

import com.aleksm.simpleclicktracker.common.Constants;
import com.aleksm.simpleclicktracker.model.DBResponse;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Campaign extends DBResponse{
	
	@Id private String id;
	
	private int status;
	private String statusDesc;

	private String redirectURL;;
	private List<Long> platformIds;
	private Date createDate;
	private Date lastUpdate;
	
	public Campaign(){
		this.lastUpdate = new Date();
		this.createDate = new Date();
		this.status = Constants.STATUS_OK;
	}

	public Campaign(String id, List<Long>platforms, String redirectUrl){
		this();
		this.id = id;
		this.platformIds = platforms;
		this.redirectURL = redirectUrl;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public List<Long> getPlatformIds() {
		return platformIds;
	}
	public void setPlatformIds(List<Long> platformIds) {
		this.platformIds = platformIds;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
}
