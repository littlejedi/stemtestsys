package com.testsys.web.bean;

public class ChoiceAllo {
	private int choiceAlloId;
	private String choiceIdList;
	private String userAnswerList;
	private String createTime;
	public ChoiceAllo()
	{
		choiceAlloId=0;
	}
	public int getChoiceAlloId() {
		return choiceAlloId;
	}
	public void setChoiceAlloId(int choiceAlloId) {
		this.choiceAlloId = choiceAlloId;
	}
	public String getChoiceIdList() {
		return choiceIdList;
	}
	public void setChoiceIdList(String choiceIdList) {
		this.choiceIdList = choiceIdList;
	}
	public String getUserAnswerList() {
		return userAnswerList;
	}
	public void setUserAnswerList(String userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
