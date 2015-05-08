package com.testsys.web.bean;

public class Option {
	private int optionId;
	private int choiceId;
	private String optionContent;
	private String optionImg;
	private int optionWeight;
	private String identifier;
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public int getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}
	public String getOptionContent() {
		return optionContent;
	}
	public void setOptionContent(String optionContent) {
		if(optionContent == null)
			this.optionContent = "";
		else
			this.optionContent = optionContent;
	}
	public String getOptionImg() {
		return optionImg;
	}
	public void setOptionImg(String optionImg) {
		this.optionImg = optionImg;
	}
	public int getOptionWeight() {
		return optionWeight;
	}
	public void setOptionWeight(int optionWeight) {
		this.optionWeight = optionWeight;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
