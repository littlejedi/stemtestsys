package com.testsys.web.bean;

public class Choice {
	private int choiceId;
	private String title;
	private String img;
	private String type;
	private String typeDes;
	private String course;
	private String courseDes;
	private int s;
	private int t;
	private int e;
	private int m;
	private String createTime;
	private int difficulty;
	private String difficultyDes;
	private String examiner;
	private String verifier;
	public int getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title == null)
			this.title = "";
		else
			this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		if(type.equals("K"))
			this.setTypeDes("基础知识");
		else if(type.equals("F"))
			this.setTypeDes("方法论");
	}
	public String getTypeDes() {
		return typeDes;
	}
	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
		if(course.equals("L"))
		{
			this.setCourseDes("生命科学");
		}
		else if(course.equals("M"))
		{
			this.setCourseDes("物质科学");
		}
		else if(course.equals("T"))
		{
			this.setCourseDes("技术与设计");
		}
		else if(course.equals("E"))
		{
			this.setCourseDes("地球与环境科学");
		}
		else if(course.equals("S"))
		{
			this.setCourseDes("社会及行为科学");
		}
	}
	public String getCourseDes() {
		return courseDes;
	}
	public void setCourseDes(String courseDes) {
		this.courseDes = courseDes;
	}
	public int getS() {
		return s;
	}
	public void setS(int s) {
		this.s = s;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		if(difficulty == 0)
			this.setDifficultyDes("简单");
		else if(difficulty == 1)
			this.setDifficultyDes("中等");
		else if(difficulty == 2)
			this.setDifficultyDes("难");
	}
	public String getDifficultyDes() {
		return difficultyDes;
	}
	public void setDifficultyDes(String difficultyDes) {
		this.difficultyDes = difficultyDes;
	}
	public String getExaminer() {
		return examiner;
	}
	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
}
