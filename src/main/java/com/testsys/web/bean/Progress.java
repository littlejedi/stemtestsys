package com.testsys.web.bean;

public class Progress {
	private int progressId;
	private int choiceAlloId;
	private int curChoiceId;
	private int beginTime;
	
	public Progress()
	{
		progressId=0;
	}
	public int getChoiceAlloId() {
		return choiceAlloId;
	}
	public void setChoiceAlloId(int choiceAlloId) {
		this.choiceAlloId = choiceAlloId;
	}
	public int getCurChoiceId() {
		return curChoiceId;
	}
	public void setCurChoiceId(int curChoiceId) {
		this.curChoiceId = curChoiceId;
	}
	public int getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}
	public int getProgressId() {
		return progressId;
	}
	public void setProgressId(int progressId) {
		this.progressId = progressId;
	}
}
