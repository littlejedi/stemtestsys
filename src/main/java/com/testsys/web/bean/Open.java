package com.testsys.web.bean;

public class Open {
	private int openId;
	private String title;
	private String img;
	private String as_example;
	private String test_point;
	private String description;
	private String course;
	private String courseDes;
	/*private String difficulty;*/
	private String createTime;
	private int difficulty;
	private String difficultyDes;
	private String examiner;
	private String verifier;
	//private String examiner;
	//private String is_verify;
	//private String verifier","modified_recommend"
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title == null)
			this.title = "";
		else
			this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getAs_example() {
		return as_example;
	}
	public void setAs_example(String as_example) {
		this.as_example = as_example;
	}
	public String getTest_point() {
		return test_point;
	}
	public void setTest_point(String test_point) {
		this.test_point = test_point;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public int getOpenId() {
		return openId;
	}
	public void setOpenId(int openId) {
		this.openId = openId;
	}
}
