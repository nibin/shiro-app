package com.msi.shiro.demo.model;

public class UrlConfigModel {

	private String urlPath;
	private String filterList;
	private Integer priorityOrder;

	public UrlConfigModel() {

		super();
	}

	public String getUrlPath() {

		return urlPath;
	}

	public void setUrlPath(String urlPath) {

		this.urlPath = urlPath;
	}

	public String getFilterList() {

		return filterList;
	}

	public void setFilterList(String filterList) {

		this.filterList = filterList;
	}

	public Integer getPriorityOrder() {

		return priorityOrder;
	}

	public void setPriorityOrder(Integer priorityOrder) {

		this.priorityOrder = priorityOrder;
	}
}
