package com.testsys.web.bean;

import com.testsys.constants.CommonConstant;

public class Myexam {
	private int examId;
	private String examSociety;
	private String examSubject;
	private String examBegin;
	private String examEnd;
	private String statusDes;
	private int status;
	private int paperId;
	private String nextStep;
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
		this.setStatusDes(CommonConstant.statusArr[status]) ;
		this.setNextStep(CommonConstant.nextStepArr[status]);
		
	}
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
	public String getExamSociety() {
		return examSociety;
	}
	public void setExamSociety(String examSociety) {
		this.examSociety = examSociety;
	}
	public String getExamSubject() {
		return examSubject;
	}
	public void setExamSubject(String examSubject) {
		this.examSubject = examSubject;
	}
	public String getNextStep() {
		return nextStep;
	}
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
}
