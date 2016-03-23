package com.aleksm.simpleclicktracker.persistence.entity;

import java.util.Date;

import com.aleksm.simpleclicktracker.model.DBResponse;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Click extends DBResponse{
	
	@Id Long id;
	@Index String campaignId;
	@Index Long platform;
	Date createDate;
	
	private int status;
	private String statusDesc;
	private int resultNumber;
	
	public Click (){
		this.createDate = new Date();
	}
	
	public Click(Long id, String campaignId, Long platform){
		this();
		this.id = id;
		this.campaignId = campaignId;
		this.platform = platform;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public Long getPlatform() {
		return platform;
	}
	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public int getResultNumber() {
		return resultNumber;
	}

	public void setResultNumber(int resultNumber) {
		this.resultNumber = resultNumber;
	}
	
}
