package com.ag.zhaisujie;

import java.io.Serializable;

/**
 *    Order.java
 *     <p>
 *     订单实体类信息
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class Order implements Serializable  {
	private String taskId;
	private String content;
	private String refuse;
	private String linkman;
	private String linkmobile;
	private String address;
	private double longitude;
	private double latitude;
	private String begin_time;
	private int clean_hours;
	private double amnt;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public double getAmnt() {
		return amnt;
	}
	public void setAmnt(double amnt) {
		this.amnt = amnt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRefuse() {
		return refuse;
	}
	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmobile() {
		return linkmobile;
	}
	public void setLinkmobile(String linkmobile) {
		this.linkmobile = linkmobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public int getClean_hours() {
		return clean_hours;
	}
	public void setClean_hours(int clean_hours) {
		this.clean_hours = clean_hours;
	}
	
}
