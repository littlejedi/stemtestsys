package com.testsys.admin.bean;

public class Page {
	private boolean isFirst;//是否为首页
	private boolean isLast;//是否为尾页
	private int isCur;
	private int isDisabled;
	private String name;
	private String link;//地址
	public Page()
	{
		isCur = 0;
		name = "";
		link = "";
		isDisabled = 0;
		
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isLast() {
		return isLast;
	}
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	public int getIsCur() {
		return isCur;
	}
	public void setCur(int isCur) {
		this.isCur = isCur;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getIsDisabled() {
		return isDisabled;
	}
	public void setDisabled(int isDisabled) {
		this.isDisabled = isDisabled;
	}
	
}
