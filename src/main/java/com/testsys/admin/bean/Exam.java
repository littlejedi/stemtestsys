package com.testsys.admin.bean;

import java.io.Serializable;

public class Exam  implements Serializable{
	private int examId;
	private int adminId;
	private String examName;
	private String examSociety;
	private String examSubject;
	private String examBegin;
	private String examEnd;
	private double examLine;
	private String notice;
	private String createTime;
	private int choiceTime;
	private int choiceNumber;
	private int signInNumbers;//��������
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
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
	public double getExamLine() {
		return examLine;
	}
	public void setExamLine(double examLine) {
		this.examLine = examLine;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getSignInNumbers() {
		return signInNumbers;
	}
	public void setSignInNumbers(int signInNumbers) {
		this.signInNumbers = signInNumbers;
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
