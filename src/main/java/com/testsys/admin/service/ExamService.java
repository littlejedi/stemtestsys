package com.testsys.admin.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testsys.admin.bean.Exam;
import com.testsys.admin.dao.ExamDao;

@Service
public class ExamService {
    
    @Autowired
    private ExamDao examDao;

    public List<Exam> getExamList(int page,int pagenum)
    {
    	List<Exam> examlist=examDao.getExamList(page*pagenum-pagenum, pagenum);
    	Iterator<Exam> itr=examlist.listIterator();
    	while(itr.hasNext())
    	{
    		Exam exam = itr.next();
    		int signInNumbers=examDao.getSignInNumbers(exam.getExamId());
    		exam.setSignInNumbers(signInNumbers);
    	}
    	return examlist;
    }
    
    public Exam getExamInfoById(int examId)
    {
    	return examDao.getExamInfoById(examId);
    }
/*    public Exam getExamInfoByName(String examName)
    {
    	return examDao.getExamInfoByName(examName);
    }*/
    //ɾ����
   /* public boolean removeExam(String examIdList)
    {
    	String[] examIdArr= examIdList.split(",");
    	//�鿴�Ƿ���¼�����Ŀ���еĻ��޷�ɾ��
    	for(int i = 0 ; i < examIdArr.length ; i++)
    	{
    		int examId=Integer.valueOf(examIdArr[i]);
    		if(false)//this.getSignInNumbers(examId) > 0)
        		;
        	else
        	{
        		this.removeExam(examId);
        	}
    	}
    	return true;
    }*/
    /*public boolean removeExam(int examId)
    {
    	return examDao.removeExam(examId);
    }*/
    //���ӿ���
   /* public int addExam(String examBegin, String examEnd, double examLine,int choiceNumber,int choiceTime, String notice, int adminId)
    {
    	return examDao.addExam(examBegin, examEnd, notice, examLine,choiceNumber,choiceTime, adminId);
    }*/
    
}
