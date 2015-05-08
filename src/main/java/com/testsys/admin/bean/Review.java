package com.testsys.admin.bean;

public class Review {
	private int examId;
	private int userId;
	private String examBegin;
	private String examEnd;
	private String status;
	private int reviewId;
	private String name;
	private String[] statusArr={"未评阅",/*"正在评阅",*/"已评阅"};
	private int hasResult;
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
	public String getExamBegin() {
		return examBegin;
	}
	public void setExamBegin(String examBegin) {
		this.examBegin = examBegin;
	}
	public String getExamEnd() {
		return examEnd;
	}
	public void setExamEnd(String examEnd) {
		this.examEnd = examEnd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = statusArr[status];
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHasResult() {
		return hasResult;
	}

	public void setHasResult(int hasResult) {
		this.hasResult = hasResult;
	}

}
