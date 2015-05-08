package com.testsys.web.bean;

public class Paper {
	private int paperId;
	private int examId;
	private int userId;
	private int choiceAlloId;
	private int openAlloId;
	private int payId;
	private int resultId;
	private int status;
	private String createTime;
	public int getPaperId() {
		return paperId;
	}
	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getChoiceAlloId() {
		return choiceAlloId;
	}
	public void setChoiceAlloId(int choiceAlloId) {
		this.choiceAlloId = choiceAlloId;
	}
	public int getOpenAlloId() {
		return openAlloId;
	}
	public void setOpenAlloId(int openAlloId) {
		this.openAlloId = openAlloId;
	}
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}