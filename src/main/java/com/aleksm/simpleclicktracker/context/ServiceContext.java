package com.aleksm.simpleclicktracker.context;

public class ServiceContext {

	private String remoteAddr;

//	private String deviceId;
	private String requestId;
	
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
//	public String getDeviceId() {
//		return deviceId;
//	}
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
