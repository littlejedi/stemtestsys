package com.testsys.admin.bean;

import com.testsys.constants.CommonConstant;

public class SignIn {
	private int paperId;
	
	private int userId;
	
	private int examId;
	
	private String name;
	
	private String idcard;
	
	private String telephoneNumber;
	
	private String course;
	
	private String status;
	
	private String createTime;
	
	private int hasResult;
	

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephone) {
		this.telephoneNumber = telephone;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if(course.equals("L"))
			this.course = "生命科学";
		else if(course.equals("M"))
			this.course = "物质科学";
		else if(course.equals("T"))
			this.course = "技术与设计";
		else if(course.equals("E"))
			this.course = "地球与环境科学";
		else if(course.equals("S"))
			this.course = "社会及行为科学";
		else
			this.course = "未选择";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = CommonConstant.statusArr[status];
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getHasResult() {
		return hasResult;
	}

	public void setHasResult(int hasResult) {
		this.hasResult = hasResult;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
