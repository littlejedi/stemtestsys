package com.testsys.web.bean;

public class Exam {
	private int examId;
	private String examName;
	private String examSociety;
	private String examSubject;
	private String examBegin;
	private String examEnd;
	private String statusDes;
	private int status;
	private int choiceTime;
	private int choiceNumber;
	private String[] statusArr={"正常","已过期","已报名"};
	public String getExamName(){
		return examName;
	}
	public void setExamName(String examName){
		this.examName = examName;
	}
	public String getExamSociety(){
		return examSociety;
	}
	public void setExamSociety(String examSociety){
		this.examSociety = examSociety;
	}
	public String getExamSubject(){
		return examSubject;
	}
	public void setExamSubject(String examSubject){
		this.examSubject = examSubject;
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
	public String getStatusDes() {
		return statusDes;
	}
	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
		this.setStatusDes(statusArr[status]) ;
		
	}
	
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getChoiceTime() {
		return choiceTime;
	}
	public void setChoiceTime(int choiceTime) {
		this.choiceTime = choiceTime;
	}
	public int getChoiceNumber() {
		return choiceNumber;
	}
	public void setChoiceNumber(int choiceNumber) {
		this.choiceNumber = choiceNumber;
	}
}

