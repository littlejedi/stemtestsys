package com.testsys.admin.bean;

import java.io.Serializable;

public class Admin implements Serializable {
	//����Աid
    private int adminId;
    //����Ա����
    private String name;
    //����Ա����
    private String password;
    //����Ա��ɫ��
    private int role;
    //����Ա�˺�
    private String account;
    //�ľ���ʦ��Ŀ
    private String course;
    //ѡ��������
    private int choiceNum;
    //����������
    private int openNum;
    //�ľ���ʦ�Ծ�����
    private int paperNum;
    //����ʱ��
    private String createTime;
    
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if(course.equals("L"))
		{
			this.course = "生命科学";
		}
		else if(course.equals("M"))
		{
			this.course = "物质科学";
		}
		else if(course.equals("T"))
		{
			this.course = "技术与设计";
		}
		else if(course.equals("E"))
		{
			this.course = "地球与环境科学";
		}
		else if(course.equals("S"))
		{
			this.course = "社会及行为科学";
		}
		//this.course = course;
	}

	public int getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(int choiceNum) {
		this.choiceNum = choiceNum;
	}

	public int getOpenNum() {
		return openNum;
	}

	public void setOpenNum(int openNum) {
		this.openNum = openNum;
	}

	public int getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(int paperNum) {
		this.paperNum = paperNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
