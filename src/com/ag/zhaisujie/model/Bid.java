package com.ag.zhaisujie.model;

import java.util.ArrayList;
import java.util.List;

public class Bid {

	private String bid_status = "";
	private String created = "";
	private Ayi ayi;
	private List<ProcessPo> processList = new ArrayList<ProcessPo>();

	public String getBid_status() {
		return bid_status;
	}

	public void setBid_status(String bid_status) {
		this.bid_status = bid_status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Ayi getAyi() {
		return ayi;
	}

	public void setAyi(Ayi ayi) {
		this.ayi = ayi;
	}

	public List<ProcessPo> getProcessList() {
		return processList;
	}

	public void setProcessList(List<ProcessPo> processList) {
		this.processList = processList;
	}
}
