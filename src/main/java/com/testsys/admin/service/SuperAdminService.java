package com.testsys.admin.service;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.Exam;
import com.testsys.admin.bean.Page;
import com.testsys.admin.bean.PageHelper;
import com.testsys.admin.bean.Result;
import com.testsys.admin.bean.Review;
import com.testsys.admin.bean.SignIn;
import com.testsys.admin.dao.AdminChoiceDao;
import com.testsys.admin.dao.AdminDao;
import com.testsys.admin.dao.ExamDao;
import com.testsys.constants.CommonConstant.paperStatus;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.ChoiceAllo;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Option;
import com.testsys.web.bean.Paper;
import com.testsys.web.bean.PaperState;
import com.testsys.web.dao.StemDao;

@Service
public class SuperAdminService {

    @Autowired
    private AdminDao adminDao;
    
    @Autowired
    private StemDao stemDao;
    
    @Autowired
    private ExamDao examDao;
    
    @Autowired
	private AdminChoiceDao choicedao;

   // @Autowired
   // private LoginLogDao loginLogDao;

    public boolean hasMatchAdmin(String account, String password) {
        int matchCount = adminDao.getMatchCount("es_admin", "account = '"+account+"' and password = '"+password+"'");//adminDao.getMatchUserCount(account, password);
        return matchCount > 0;
    }

    public Admin findUserByUserName(String account) {
        return adminDao.findUserByUserName(account);
    }
    
    public List<Admin> getAdminList(int page,int pagenum,int role)
    {
    	///return adminDao.getAdminList(page*pagenum-pagenum, pagenum,role);
    	List<Admin> adminlist=adminDao.getAdminList(page*pagenum-pagenum, pagenum,role);
    	Iterator<Admin> itr=adminlist.listIterator();
    	while(itr.hasNext())
    	{
    		Admin admin = itr.next();
    		admin.setChoiceNum(adminDao.getChoiceNumbers(admin.getAdminId()));
    		admin.setOpenNum(adminDao.getOpenNumbers(admin.getAdminId()));
    		admin.setPaperNum(adminDao.getPaperNumbers(admin.getAdminId()));
    		//int signInNumbers=examDao.getSignInNumbers(exam.getExamId());
    		//exam.setSignInNumbers(signInNumbers);
    	}
    	return adminlist;
    }
    
    public int addAdmin(String account,int role,String name,String password,String course)
    {
    	return adminDao.addAdmin(account, role, name, password, course);
    }
  //ɾ�����Ա
    public boolean removeAdmin(String adminIdList)
    {
    	String[] adminIdArr= adminIdList.split(",");
    	//�鿴�Ƿ���¼�����Ŀ���еĻ��޷�ɾ��
    	for(int i = 0 ; i < adminIdArr.length ; i++)
    	{
    		int adminId=Integer.valueOf(adminIdArr[i]);
    		if(this.getChoiceNumbers(adminId) > 0 || this.getOpenNumbers(adminId) > 0 || this.getPaperNumbers(adminId) > 0)
        		;
        	else
        	{
        		this.removeAdmin(adminId);
        	}
    	}
    	return true;
    	//return adminDao.removeAdmin(adminIdList);
    }
    
    public boolean multiCalcu(String paperIdList)
    {
    	String[] paperIdArr= paperIdList.split(",");
    	//�鿴�Ƿ���¼�����Ŀ���еĻ��޷�ɾ��
    	for(int i = 0 ; i < paperIdArr.length ; i++)
    	{
    		int paperId=Integer.valueOf(paperIdArr[i]);
    		this.calculateChoiceResult(paperId);
    	}
    	return true;
    	//return adminDao.removeAdmin(adminIdList);
    }
    public void calculateChoiceResult(int paperId)
	{
		Paper paper = stemDao.getPaperInfo(paperId);
		ChoiceAllo ca=stemDao.findChoiceAllo(paper.getChoiceAlloId());
		String[] alloListArr=ca.getChoiceIdList().split(",");
		if(ca.getUserAnswerList() == null)
		{
			int resultId = stemDao.insertAction("es_exam_result", "choice_s,choice_t,choice_e,choice_m,choice_score,status", "0,0,0,0,0,2");
			updatePaperStatus(paperStatus.CHOICENOTPASS.ordinal(),paperId);
			stemDao.updateAction("es_exam_paper", "result_id = "+resultId +" ", "paper_id = "+paperId);
			return ;
		}
		String[] userAnswerList = ca.getUserAnswerList().split(",");
		double [] result = new double[9];
		for(int r = 0;r < 9 ; r++)
		{
			result[r]=0;
		}
		for(int i= 0;i < alloListArr.length ; i++)
		{
			if(!alloListArr[i].equals("") && !userAnswerList[i].equals(""))
			{
				int choiceId = Integer.valueOf(alloListArr[i]);
				int optionId = Integer.valueOf(userAnswerList[i]);
				
				Option option = stemDao.findOptionInfoByOptionId(optionId);
				Choice choice = stemDao.findChoiceInfoById(choiceId);
				
				List<Option> optionlist = stemDao.findOptionListByChoiceId(choiceId);
				Iterator<Option> iter = optionlist.listIterator();
				boolean isHasOption = false;//�鿴ѡ�����Ƿ��д�ѡ��
				while(iter.hasNext())
				{
					Option op = iter.next();
					if(op.getOptionId() == optionId)
					{
						isHasOption = true;
						break;
					}
				}
				if(isHasOption)
				{
					result[0] += choice.getS()*option.getOptionWeight()*0.01;
					
					result[1] += choice.getT()*option.getOptionWeight()*0.01;
					result[2] += choice.getE()*option.getOptionWeight()*0.01;
					result[3] += choice.getM()*option.getOptionWeight()*0.01;
					
					result[5] += choice.getS()*3*0.01;
					result[6] += choice.getT()*3*0.01;
					result[7] += choice.getE()*3*0.01;
					result[8] += choice.getM()*3*0.01;
					
					result[4] += option.getOptionWeight();
				}
			}
		}
		boolean isPass = false;
		Exam exam = examDao.getExamInfoById(paper.getExamId());
		if(result[4] >= exam.getExamLine())
			isPass = true;
		int resultId = stemDao.insertAction("es_exam_result", "choice_s,choice_t,choice_e,choice_m,choice_score,status", (result[5]==0?0:(result[0]*100/result[5]))+","+(result[6]==0?0:(result[1]*100/result[6]))+","+(result[7]==0?0:(result[2]*100/result[7]))+","+(result[8]==0?0:(result[3]*100/result[8]))+","+result[4]+","+(isPass ? "1" : "2"));
		
		if(isPass && paper.getStatus() == 5)
		{
			updatePaperStatus(paperStatus.CHOICEPASS.ordinal(),paperId);
			stemDao.updateAction("es_exam_paper", "result_id = "+resultId +" ", "paper_id = "+paperId);
		}
		if(!isPass && paper.getStatus() == 5)
		{
			updatePaperStatus(paperStatus.CHOICENOTPASS.ordinal(),paperId);
			stemDao.updateAction("es_exam_paper", "result_id = "+resultId +" ", "paper_id = "+paperId);
		}
	}
    public void updatePaperStatus(int status,int paperId)
	{
		stemDao.updateAction("es_exam_paper", " status = "+status, "paper_id = "+paperId);
	}
    public boolean removeAdmin(int adminId)
    {
    	return adminDao.removeAdmin(adminId);
    }
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
    	//Exam exam = examlist.listIterator();
    	//for(int i=0;i<examlist.listIterator())
    	return examlist;
    }
    public Exam getExamInfoById(int examId)
    {
    	return examDao.getExamInfoById(examId);
    }
    public Admin getAdminInfoById(int adminId)
    {
    	return adminDao.findUserByAdminId(adminId);
    }
    public boolean removeExam(String examIdList)
    {
    	String[] examIdArr= examIdList.split(",");
    	//�鿴�Ƿ���¼�����Ŀ���еĻ��޷�ɾ��
    	for(int i = 0 ; i < examIdArr.length ; i++)
    	{
    		int examId=Integer.valueOf(examIdArr[i]);
    		if(this.getSignInNumbers(examId) > 0)
        		;
        	else
        	{
        		this.removeExam(examId);
        	}
    	}
    	return true;
    	//return adminDao.removeAdmin(adminIdList);
    }
    public boolean removeExam(int examId)
    {
    	return examDao.removeExam(examId);
    }
    ///��ȡϰ�����Ա¼���ѡ������Ŀ
    public int getChoiceNumbers(int adminId)
    {
    	return adminDao.getChoiceNumbers(adminId);
    }
    //��ȡϰ�����Ա¼��Ŀ�������Ŀ
    public int getOpenNumbers(int adminId)
    {
    	return adminDao.getOpenNumbers(adminId);
    }
    //��ȡ������ľ���ʦ���Ծ���Ŀ
    public int getPaperNumbers(int adminId)
    {
    	return adminDao.getPaperNumbers(adminId);
    }
    //��ȡ���Եı�������
    public int getSignInNumbers(int examId)
    {
    	return examDao.getSignInNumbers(examId);
    }
    public int getTimeOutNumbers()
    {
    	return examDao.getMatchCount("es_exam_paper", "status = 5");
    }
    
    public int addExam(String examName,String Society,String Subject,String examBegin, String examEnd, double examLine, String notice,int choiceNumber,int choiceTime, int adminId)
    {
    	return examDao.addExam(examName,Society,Subject,examBegin, examEnd, notice, examLine,choiceNumber,choiceTime, adminId);
    }
    /*public void loginSuccess(User user) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        loginLogDao.insertLoginLog(loginLog);
    }*/
    public List<SignIn> getSignInList(int examId,int page,int pagenum)
    {
    	return examDao.getSignInList(examId, page*pagenum-pagenum, pagenum);
    }
    public List<SignIn> getTimeOutList(int page,int pagenum)
    {
    	return examDao.getTimeOutList(page*pagenum-pagenum, pagenum);
    }
    public List<Review> getReviewList(int adminId,int page,int pagenum)
    {
    	return adminDao.getReviewList(adminId, page*pagenum-pagenum, pagenum);
    }
    
    public List<Choice> getChoiceList(int page, int pagenum, int adminId) {
		// TODO Auto-generated method stub
		List<Choice> choicelist=choicedao.getChoiceList(page*pagenum-pagenum, pagenum,adminId,"");
		return choicelist;
	}
    public List<Open> getOpenList(int page, int pagenum, int adminId) {
		// TODO Auto-generated method stub
		List<Open> openlist=choicedao.getOpenList(page*pagenum-pagenum, pagenum,adminId,"");
		return openlist;
	}
	public int getMatchChoiceCount(int adminId)
	{
		return choicedao.getMatchChoiceCount(adminId,"");
	}
	public int getMatchOpenCount(int adminId)
	{
		return choicedao.getMatchOpenCount(adminId,"");
	}
	public int getMatchAdminCount(int role)
	{
		return adminDao.getMatchAdminCount(role);
	}
	public int getMatchExamCount()
	{
		return examDao.getMatchExamCount();
	}
	/*public int getMatchSignInCount(int examId)
	{
		return examDao.getMatchSignInCount(examId);
	}
	public int getMatchReviewCount(int adminId)
	{
		return examDao.getMatchReviewCount(adminId);
	}*/
	public String getChoiceInfo(int choiceId)
	{
		Choice choice = choicedao.findChoiceInfoById(choiceId);
		List<Option> options=choicedao.findOptionListByChoiceId(choiceId);
		JSONObject json=new JSONObject();
		JSONArray optionList = new JSONArray();
		Iterator<Option> itr=options.listIterator();
		//char identifier = 'A';
    	while(itr.hasNext())
    	{
    		Option option = itr.next();
    		JSONObject optionObj = new JSONObject();
    		optionObj.put("identifier", option.getIdentifier());
    		optionObj.put("content", option.getOptionContent());
    		//��Ҫƴ����ɿ�ֱ�ӷ��ʵĵ�ַ
    		optionObj.put("img", option.getOptionImg());
    		optionObj.put("point", option.getOptionWeight());
    		optionList.add(optionObj);
    	}
    	json.put("choiceId", choice.getChoiceId());
		json.put("title", choice.getTitle());
		json.put("img", choice.getImg());
		json.put("options", optionList);

		return json.toString();
	}
	//ɾ��ѡ����
    public boolean removeChoice(String choiceIdList)
    {
    	String[] choiceIdArr= choiceIdList.split(",");
    	//������Ŀ�ѱ�ѡ����뿼�ԣ��ܷ�ɾ��
    	for(int i = 0 ; i < choiceIdArr.length ; i++)
    	{
    		int choiceId=Integer.valueOf(choiceIdArr[i]);
    		choicedao.removeChoice(choiceId);
    	}
    	return true;
    	//return adminDao.removeAdmin(adminIdList);
    }
    
    public List<Page> getPageList(String path,int page,int num,int count)
	{
		PageHelper p = new PageHelper();
        p.setTotal(count);
        p.setPageSize(num);
        p.setPath(/*"/bb/article/list/questionlist.html?page="*/path);
        p.setIndex(page);
        return p.getPageList();
	}

	public String getExamResultJson(int paperId) {
		// TODO Auto-generated method stub
		JSONArray resultList = new JSONArray();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getStatus() < paperStatus.CHOICENOTPASS.ordinal() || paper.getStatus() == paperStatus.CHOICEEXPIRED.ordinal())
		{
			JSONObject jo = new JSONObject();
			jo.put("status", "noresult");
			return jo.toString();
		}
		Result result = examDao.getResult(paper.getResultId());//
		if(paper.getStatus() == paperStatus.OPENREVIEWED.ordinal())
		{
			//resultList.add(getOpenResult(paperId));
			List<com.testsys.web.bean.Review> reviewlist = stemDao.getReviewList(paper.getOpenAlloId());
			for(int i = 0; i < 3;i++)
			{
				JSONObject jo = new JSONObject();
				jo.put("type", "open");
				jo.put("order", i);
				if(i == 2)
				{
					//添加开放题总分
					jo.put("s", result.getOpens());
					jo.put("t", result.getOpent());
					jo.put("e", result.getOpene());
					jo.put("m", result.getOpenm());
					jo.put("score", result.getOpenscore());
					jo.put("tname", "合计");
				}
				else
				{
					jo.put("s", reviewlist.get(i).getS());
					jo.put("t", reviewlist.get(i).getT());
					jo.put("e", reviewlist.get(i).getE());
					jo.put("m", reviewlist.get(i).getM());
					jo.put("score", reviewlist.get(i).getReviewResult());
					Admin admin = adminDao.findUserByAdminId(reviewlist.get(i).getAdminId());
					jo.put("tname", admin.getName());
				}
				resultList.add(jo);
			}
		}
		Exam exam = examDao.getExamInfoById(paper.getExamId());
		//添加选择题成绩
		JSONObject jo = new JSONObject();
		jo.put("s", result.getChoices());
		jo.put("t", result.getChoicet());
		jo.put("e", result.getChoicee());
		jo.put("m", result.getChoicem());
		jo.put("score", result.getChoicescore());
		jo.put("type", "choice");
		jo.put("examline", exam.getExamLine());
		jo.put("examsum", exam.getChoiceNumber()*3);
		resultList.add(jo);
		return resultList.toString();
	}

	public int getMatchUserCountByIdcard(String idcard) {
		// TODO Auto-generated method stub
		return adminDao.getMatchCount("es_user", "idcard='"+idcard+"'");
	}

	public void addExamTimes(String idcard, int examnum) {
		// TODO Auto-generated method stub
		adminDao.updateAction("es_user", "exam_times=exam_times+"+examnum,"idcard='"+idcard+"'");
	}
public List<Result> getResultbyinfo(String society,String subject,String examname)
{
	List<Result> resultlist=examDao.getResultbyinfo(society,subject,examname);
/*	Iterator<Result> itr=resultlist.listIterator();
	while(itr.hasNext())
	{
		Result result = itr.next();
		int signInNumbers=examDao.getSignInNumbers(result.getExamId());
		exam.setSignInNumbers(signInNumbers);
	}*/
	//Exam exam = examlist.listIterator();
	//for(int i=0;i<examlist.listIterator())
	return resultlist;
}
}
