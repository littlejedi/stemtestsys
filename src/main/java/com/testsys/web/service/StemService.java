package com.testsys.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testsys.admin.bean.Exam;
import com.testsys.admin.dao.ExamDao;
import com.testsys.constants.CommonConstant.paperStatus;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.ChoiceAllo;
import com.testsys.web.bean.ChoiceResult;
import com.testsys.web.bean.Myexam;
import com.testsys.web.bean.OpenAllo;
import com.testsys.web.bean.Option;
import com.testsys.web.bean.Paper;
import com.testsys.web.bean.PaperState;
import com.testsys.web.bean.Profile;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Review;
import com.testsys.web.dao.StemDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StemService {
	
	@Autowired
	private StemDao stemDao;
	 
	@Autowired
	private ExamDao examDao;
	 
	//��ȡ���п����б�
	public List<com.testsys.web.bean.Exam> getExamList(int userId, int page, int pagenum) 
	{
		// TODO Auto-generated method stub
    	return stemDao.getExamList(userId, page*pagenum-pagenum, pagenum);
	}
	
	//������
	public String signIn(int userId,int examId)
	{
		String status = "";
		JSONObject json=new JSONObject();
		Profile profile = stemDao.getProfileInfo(userId);
		if(profile.getUserId() == 0 ||profile.getIdcard().equals("") || profile.getTelephonenumber().equals(""))
		{
			status = "noinfo";
		}
		else
		{
			Exam exam = examDao.getExamInfoById(examId);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int result = nowstr.compareTo(exam.getExamEnd());
			if(result > 0)
			{
				status = "expired";
			}
			else if(stemDao.getMatchExamPaperCount(userId, examId) > 0)
			{
				status = "duplicate";
			}
			else
			{
				//���㱨������������
				int paperId = stemDao.registerExam(userId, examId);
				pay(userId,paperId);
				status = "success";
			}
		}
		json.put("status", status);
		return json.toString();
	}
	
	public String newSignIn(int userId,String examName)
	{
		String status = "";
		JSONObject json=new JSONObject();
		Profile profile = stemDao.getProfileInfo(userId);
		if(profile.getUserId() == 0 ||profile.getIdcard().equals("") /*|| profile.getTelephonenumber().equals("")*/)
		{
			status = "noinfo";
		}
		else
		{
			//Exam exam = examDao.getExamInfoById(examId);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			Date now_t = new Date(now.getTime() + 20000);
			String nowstr=sdf.format(now_t);
    		Exam exam=examDao.getExamInfoByName(examName,nowstr);
    		int examId=exam.getExamId();
			//int examId = stemDao.getNearestExam(nowstr);
			if(examId == 0)
			{
				status="notfound";
			}
			else if(stemDao.getMatchExamPaperCount(userId, examId) > 0)
			{
				status = "duplicate";
				int paperId = stemDao.getPaperId(userId, examId);
				json.put("paperId", paperId);
			}
			else
			{
				//���㱨������������
				if(profile.getExamTimes() == 0)
				{
					status = "notimes";
				}
				else
				{
					int paperId = stemDao.registerExam(userId, examId);
					stemDao.updateAction("es_user", "exam_times = exam_times - 1", "user_id="+userId);
					pay(userId,paperId);
					status = "success";
					json.put("paperId", paperId);
				}
			}
		}
		json.put("status", status);
		return json.toString();
	}
	
	public String pay(int userId,int paperId)
	{
		String status = "";
		JSONObject json=new JSONObject();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId )//|| paper.getStatus() != 0)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int result = nowstr.compareTo(exam.getExamEnd());
			if(result > 0)
			{
				status = "expired";
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEEXPIRED))
					updatePaperStatus(paperStatus.CHOICEEXPIRED.ordinal(),paperId);
			}
			else
			{
				
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.HASPAY))
				{
					status = "success";
					updatePaperStatus(paperStatus.HASPAY.ordinal(),paperId);
				}
				else
					status = "noauth";
			}
		}
		json.put("status", status);
		return json.toString();
	}
	
	public String beginExam(int userId,int paperId)
	{
		String status = "";
		JSONObject json=new JSONObject();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId)// || paper.getStatus() != 1)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int begin = nowstr.compareTo(exam.getExamBegin());
			int end = nowstr.compareTo(exam.getExamEnd());
			if(begin < 0)
			{
				status = "notbegin";
			}
			else if(end > 0)
			{
				status = "expired";
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEEXPIRED))
					updatePaperStatus(paperStatus.CHOICEEXPIRED.ordinal(),paperId);
			}
			else
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEING))
				{
					status = "success";
					updatePaperStatus(paperStatus.CHOICEING.ordinal(),paperId);
					generateChoiceList(paperId);
				}
				else
					status = "noauth";
				
			}
		}
		json.put("status", status);
		return json.toString();
	}
	//��ȡ�ұ���Ŀ����б�
	public List<Myexam> getMyExamList(int userId,int page,int pagenum)
    {
    	return stemDao.getMyExamList(userId, page*pagenum-pagenum, pagenum);
    }
	
	
	public String submitChoice(int paperId,int userId,String resultIdList, int type) throws ParseException
	{
		String status = "";
		JSONObject json=new JSONObject();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId || paper.getStatus() != 2)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int begin = nowstr.compareTo(exam.getExamBegin());
			if(begin < 0)
			{
				status = "notbegin";
			}
			else
			{
				ChoiceAllo ca = getChoiceAlloById(paper.getChoiceAlloId());
	    		Date createtime = sdf.parse(ca.getCreateTime());
	    		
	    		long createseconds = createtime.getTime()/1000;
	    		
	    		long nowseconds = now.getTime()/1000;
	    		long examlimit = exam.getChoiceTime()*60;
	    		
	    		long remainingtime = examlimit - (nowseconds - createseconds);
				if(remainingtime <= -20)
				{
					status = "expired";
					if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEEXPIRED))
						updatePaperStatus(paperStatus.CHOICEEXPIRED.ordinal(),paperId);
				}
				else
				{
					if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICENOTPASS))
					{
						status = "success";
						stemDao.updateChoiceResult(paper.getChoiceAlloId(), resultIdList);
						if(type == 1)
							calculateChoiceResult(paperId);
					}
					else
						status = "noauth";
				}
			}
			
		}
		json.put("status", status);
		return json.toString();
	}
	public List<Option> getOptionListByChoiceId(int choiceId)
	{
		return stemDao.findOptionListByChoiceId(choiceId);
	}
	public String generateWarningJson(String errorInfo,String errorType)
	{
		JSONObject json=new JSONObject();
		
		json.put("title", errorInfo);
		json.put("status", errorType);

		return json.toString();
	}
	
	public String generateAllAnswerJson(int paperId)
	{
		Paper paper = getPaperInfo(paperId);
		JSONObject json=new JSONObject();
		JSONArray choiceList = new JSONArray();
		
		ChoiceAllo ca = stemDao.findChoiceAllo(paper.getChoiceAlloId());
		String[] choices = ca.getChoiceIdList().split(",");
		String[] answers = {};
		if( ca.getUserAnswerList() != null)
			answers = ca.getUserAnswerList().split(",");
		for(int i = 0; i < choices.length ; i++)
		{
			int choiceId = Integer.valueOf(choices[i]);
			
			Choice choice = stemDao.findChoiceInfoById(choiceId);
			JSONObject choiceObj = new JSONObject();
			choiceObj.put("title", choice.getTitle());
			choiceObj.put("img", choice.getImg());
			
			int answerId = 0;
			if(answers.length > i)
				answerId = Integer.valueOf(answers[i]);
			choiceObj.put("answer", answerId);
			
			List<Option> options = stemDao.findOptionListByChoiceId(choice.getChoiceId());
			JSONArray optionList = new JSONArray();
			Iterator<Option> itr=options.listIterator();
	    	while(itr.hasNext())
	    	{
	    		Option option = itr.next();
	    		JSONObject optionObj = new JSONObject();
	    		optionObj.put("optionid", option.getOptionId());
	    		optionObj.put("content", option.getOptionContent());
	    		optionObj.put("identifier", option.getIdentifier());
	    		optionObj.put("img", option.getOptionImg());
	    		optionList.add(optionObj);
	    	}
	    	choiceObj.put("options",optionList);
	    	choiceList.add(choiceObj);
	    	System.out.println("line");
		}
		
		json.put("paperId", paper.getPaperId());
		json.put("status", "success");
		json.put("progress", "");
		json.put("remain_time", 1);
		json.put("choices", choiceList);
		return json.toString();
	}
	
	public String generateAllAnswerJson2(int paperId)
	{
		Paper paper = getPaperInfo(paperId);
		
		ChoiceAllo ca = stemDao.findChoiceAllo(paper.getChoiceAlloId());
		List<Choice> choices = stemDao.findAllChoiceByChoiceId(ca.getChoiceIdList());
		System.out.println(ca.getChoiceIdList()+","+choices.size());
		JSONObject json=new JSONObject();
		JSONArray choiceList = new JSONArray();
		Iterator<Choice> citr = choices.listIterator();
		while(citr.hasNext())
		{
			Choice choice = citr.next();
			JSONObject choiceObj = new JSONObject();
			choiceObj.put("title", choice.getTitle());
			choiceObj.put("img", choice.getImg());
			
			List<Option> options = stemDao.findOptionListByChoiceId(choice.getChoiceId());
			JSONArray optionList = new JSONArray();
			Iterator<Option> itr=options.listIterator();
	    	while(itr.hasNext())
	    	{
	    		Option option = itr.next();
	    		JSONObject optionObj = new JSONObject();
	    		optionObj.put("optionid", option.getOptionId());
	    		optionObj.put("content", option.getOptionContent());
	    		optionObj.put("identifier", option.getIdentifier());
	    		optionObj.put("img", option.getOptionImg());
	    		optionList.add(optionObj);
	    	}
	    	choiceObj.put("options",optionList);
	    	choiceList.add(choiceObj);
	    	System.out.println("line");
		}
		
		
		
		json.put("paperId", paper.getPaperId());
		json.put("status", "success");
		json.put("progress", "");
		json.put("remain_time", 1);
		json.put("choices", choiceList);
		return json.toString();
	}
	public void generateChoiceList(int paperId)
	{
		Paper paper = stemDao.getPaperInfo(paperId);
		Exam exam = examDao.getExamInfoById(paper.getExamId());
		List<Integer> choiceid = stemDao.getRandomChoiceList(exam.getChoiceNumber());
		//Collections.sort(choiceid);
		Iterator<Integer> iter = choiceid.listIterator();
		int a = 0;
		String choice_id_list ="";
		while(iter.hasNext())
		{
			if(a == 0)
				choice_id_list+=iter.next();
			else
				choice_id_list+=","+iter.next();
			a++;
		}
		int choice_allo_id = stemDao.insertAction("es_choice_allocation", "choice_id_list", "'"+choice_id_list+"'");
		stemDao.updateAction("es_exam_paper", "status = " + 2 + " , choice_allo_id = " + choice_allo_id, "paper_id="+paperId);
	}
	public void calculateChoiceResult(int paperId)
	{
		Paper paper = stemDao.getPaperInfo(paperId);
		ChoiceAllo ca=stemDao.findChoiceAllo(paper.getChoiceAlloId());
		String[] alloListArr=ca.getChoiceIdList().split(",");
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
		
		if(isPass&&PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICEPASS))
		{
			updatePaperStatus(paperStatus.CHOICEPASS.ordinal(),paperId);
			stemDao.updateAction("es_exam_paper", "result_id = "+resultId +" ", "paper_id = "+paperId);
		}
		if(!isPass&&PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.CHOICENOTPASS))
		{
			updatePaperStatus(paperStatus.CHOICENOTPASS.ordinal(),paperId);
			stemDao.updateAction("es_exam_paper", "result_id = "+resultId +" ", "paper_id = "+paperId);
		}
	}
	public Paper getPaperInfo(int paperId) {
		// TODO Auto-generated method stub
		return stemDao.getPaperInfo(paperId);
	}
	
	public Profile getProfileInfo(int userId) {
		// TODO Auto-generated method stub
		return stemDao.getProfileInfo(userId);
	}

	public String updateProfileInfo(int userId, String idcard, String phone, String realname) {
		// TODO Auto-generated method stub
		stemDao.updateAction("es_user", "idcard = '"+idcard+"',telephonenumber = '"+phone+"',realname = '"+realname+"' ", "user_id = "+userId);
		JSONObject json = new JSONObject();
		json.put("status", "success");
		return json.toString();
	}

	public ChoiceResult getChoiceResult(int resultId) {
		// TODO Auto-generated method stub
		return stemDao.getChoiceResult(resultId);
	}

	public ChoiceAllo getChoiceAlloById(int choiceAlloId) {
		// TODO Auto-generated method stub
		return stemDao.findChoiceAllo(choiceAlloId);
	}
	
	public void updatePaperStatus(int status,int paperId)
	{
		stemDao.updateAction("es_exam_paper", " status = "+status, "paper_id = "+paperId);
	}
	
	
	public String beginOpen(int paperId,int userId,String course) throws ParseException
	{
		String status = "";
		JSONObject json=new JSONObject();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId)// || paper.getStatus() != 4)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int begin = nowstr.compareTo(exam.getExamBegin());
			int end = nowstr.compareTo(exam.getExamEnd());
			if(begin < 0)
			{
				status = "notbegin";
			}
			else if(end > 0)
			{
				status = "expired";
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
			}
			else
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENBEGIN))
				{
					status = "success";
					generateOpenAllo(paperId,course);
					updatePaperStatus(paperStatus.OPENBEGIN.ordinal(),paperId);
				}
				else
					status = "noauth";
				
			}
		}
		json.put("status", status);
		return json.toString();
	}
	//随机生成3道开放题
	public void generateOpenAllo(int paperId,String course)
	{
		List<Integer> openid = stemDao.getRandomOpenList(course);
		Collections.sort(openid);
		Iterator<Integer> iter = openid.listIterator();
		int a = 0;
		String open_id_list ="";
		while(iter.hasNext())
		{
			if(a == 0)
				open_id_list+=iter.next();
			else
				open_id_list+=","+iter.next();
			a++;
		}
		int open_allo_id = stemDao.insertAction("es_open_allocation", "optional_open_id,course", "'"+open_id_list+"','"+course+"'");
		stemDao.updateAction("es_exam_paper", "open_allo_id = " + open_allo_id, "paper_id="+paperId);
	}
	//选择开放题进行考试
	public String chooseOpen(int paperId,int userId,int chooseOpenid) throws ParseException
	{
		String status = "";
		JSONObject json=new JSONObject();
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId)// || paper.getStatus() != 5)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int begin = nowstr.compareTo(exam.getExamBegin());
			int end = nowstr.compareTo(exam.getExamEnd());
			if(begin < 0)
			{
				status = "notbegin";
				//stemDao.updateAction("es_exam_paper", " status = 8", "paper_id = "+paperId);
			}
			else if(end > 0)
			{
				status = "expired";
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
				//stemDao.updateAction("es_exam_paper", " status = 9", "paper_id = "+paperId);
			}
			else
			{
				OpenAllo oa = stemDao.findOpenAllo(paper.getOpenAlloId());
				String[] aa=oa.getOptionalOpenId().split(",");
				boolean iscontain = false;
				for(int i = 0; i < aa.length; i++)
				{
					if(chooseOpenid == Integer.valueOf(aa[i]))
					{
						iscontain = true;
						break;
					}
				}
				if(!iscontain)
					status = "error";
				else
				{
					if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENCHOOSE))
					{
						stemDao.updateAction("es_open_allocation", " selected_open_id = "+chooseOpenid, "open_allo_id = " + paper.getOpenAlloId());
						updatePaperStatus(paperStatus.OPENCHOOSE.ordinal(),paperId);
						status = "success";
					}
					else
						status = "noauth";
					
				}
			}
		}
		json.put("status", status);
		return json.toString();
	}

	public List<Open> getOpenList(int openAlloId) {
		// TODO Auto-generated method stub
		OpenAllo oa = stemDao.findOpenAllo(openAlloId);
		String[] ol = oa.getOptionalOpenId().split(",");
		List<Open> openlist = new ArrayList<Open>();
		for(int i = 0; i< ol.length;i++)
		{
			openlist.add(stemDao.getOpenQuestionById(Integer.valueOf(ol[i])));
		}
		return openlist;
	}
	public Review getReview(int reviewId)
	{
		return stemDao.getReviewById(reviewId);
	}
	public boolean reviewPaper(int s,int t,int e,int m,int score,int reviewId)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		stemDao.updateAction("es_open_review", "review_time = '"+sdf.format(date)+"',s="+s+",t="+t+",e="+e+",m="+m+",review_result="+score+",status=1", "review_id="+reviewId);
		Review review = stemDao.getReviewById(reviewId);//int
		int unReviewCount = stemDao.getMatchCount("es_open_review", "`status` = 0 and open_allo_id = "+review.getOpenAlloId());
		if(unReviewCount == 0)
		{
			double rs=0;
			double ss=0;
			double ts=0;
			double es=0;
			double ms=0;
			List<Review> reviewlist = stemDao.getReviewList(review.getOpenAlloId());
			int count = reviewlist.size();
			if(count == 0) count =1;
			for(int i = 0; i <count;i++)
			{
				rs += reviewlist.get(i).getReviewResult();
				ss += reviewlist.get(i).getS();
				ts += reviewlist.get(i).getT();
				es += reviewlist.get(i).getE();
				ms += reviewlist.get(i).getM();
			}
			
			Paper paper = stemDao.getPaperInfoByOpenAlloId(review.getOpenAlloId());
			stemDao.updateAction("es_exam_result", "open_s="+(ss/count)+",open_t="+(ts/count)+",open_e="+(es/count)+",open_m="+(ms/count)+",open_score="+(rs/count), "result_id="+paper.getResultId());
			if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENREVIEWED))
				updatePaperStatus(paperStatus.OPENREVIEWED.ordinal(),paper.getPaperId());
		}
		return true;
	}
	public Open getOpenInfo(int openAlloId) {
		// TODO Auto-generated method stub
		OpenAllo oa = stemDao.findOpenAllo(openAlloId);
		return stemDao.getOpenQuestionById(Integer.valueOf(oa.getSelectedOpenId()));
	}
	public String openUpload(int paperId,int userId,String answersrc)
	{
		String status = "";
		Paper paper = stemDao.getPaperInfo(paperId);
		if(paper.getUserId() != userId)// || paper.getStatus() != 10)
			status = "noauth";
		else
		{
			Exam exam = examDao.getExamInfoById(paper.getExamId());	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			int begin = nowstr.compareTo(exam.getExamBegin());
			int end = nowstr.compareTo(exam.getExamEnd());
			if(begin < 0)
			{
				status = "notbegin";
			}
			else if(end > 0)
			{
				status = "expired";
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENEXPIRED))
					updatePaperStatus(paperStatus.OPENEXPIRED.ordinal(),paperId);
			}
			else
			{
				if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENSUBMIT))
				{
					status = "success";
					stemDao.updateAction("es_open_allocation", " answer_file_src = '"+answersrc+"'", "open_allo_id = "+paper.getOpenAlloId());
					OpenAllo oa = stemDao.findOpenAllo(paper.getOpenAlloId());
					List<Integer> adminList = stemDao.getRandomAdminList(oa.getCourse());
					for(int i=0; i<adminList.size();i++)
						stemDao.insertAction("es_open_review", "open_allo_id,admin_id", paper.getOpenAlloId()+","+adminList.get(i));
					//如果没有阅卷老师怎么办？
					if(adminList.size() != 0)
						updatePaperStatus(paperStatus.OPENSUBMIT.ordinal(),paperId);
				}
				else
					status = "noauth";
			}
		}
		return status;
	}
	
	public OpenAllo getOpenAlloById(int openAlloId) {
		// TODO Auto-generated method stub
		return stemDao.findOpenAllo(openAlloId);
	}

	public boolean checkBeginTime(int reviewId) {
		// TODO Auto-generated method stub
		Review review = stemDao.getReviewById(reviewId);
		if(review.getBeginTime().equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			stemDao.updateAction("es_open_review", "begin_time='"+sdf.format(date)+"'", "review_id="+reviewId);
			return true;
		}
		return false;
	}

	public int matchUserCount(int userId) {
		// TODO Auto-generated method stub
		return stemDao.getMatchCount("es_user", "user_id="+userId);
	}

	public void registerUser(int userId,String name, String idcard, String realname, String phone) {
		// TODO Auto-generated method stub
		stemDao.insertAction("es_user", "user_id,name,idcard,realname,telephonenumber", userId+",'"+name+"','"+idcard+"','"+realname+"','"+phone+"'");
	}

	public void updateUser(int userId, String userName, String idcard, String realname, String phone) {
		// TODO Auto-generated method stub
		stemDao.updateAction("es_user", "name = '"+userName+"',idcard='"+idcard+"',realname='"+realname+"',telephonenumber='"+phone+"'",	"user_id="+userId);
	}

	public String getChoiceResultList(String idcard) {
		JSONObject json=new JSONObject();
		int count = stemDao.getMatchCount("es_user", "idcard='"+idcard+"'");
		if(count == 0)
		{
			json.put("status", "invaliduser");
			return json.toString();
		}
		// TODO Auto-generated method stub
		List<ChoiceResult> crl = stemDao.getChoiceResultList(idcard);
		Iterator<ChoiceResult> itr = crl.listIterator();
		JSONArray crList = new JSONArray();
		while(itr.hasNext())
		{
			ChoiceResult cr = itr.next();
			JSONObject crObj = new JSONObject();
			crObj.put("score",cr.getChoicescore());
			crObj.put("s", cr.getChoices());
			crObj.put("t", cr.getChoicet());
			crObj.put("e", cr.getChoicee());
			crObj.put("m", cr.getChoicem());
    		crList.add(crObj);
		}
		json.put("results",crList);
		json.put("status", "success");
		
		return json.toString();
	}
	//根据考试名称查找考试，
	public Paper getPaperInfobyexamname(String examName) {
		// TODO Auto-generated method stub
		return stemDao.getPaperInfobyexamname(examName);
	}
	public String signInn(int userId,String examName)
	{
		String status = "";
		JSONObject json=new JSONObject();
		Profile profile = stemDao.getProfileInfo(userId);
		if(profile.getUserId() == 0 ||profile.getIdcard().equals("") || profile.getTelephonenumber().equals(""))
		{
			status = "noinfo";
		}
		else
		{		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now=new Date();
			String nowstr=sdf.format(now);
			Exam exam = examDao.getExamInfoByName(examName,nowstr);
			int result = nowstr.compareTo(exam.getExamEnd());
			int examId=exam.getExamId();
			if(result > 0)
			{
				status = "expired";
			}
			else if(stemDao.getMatchExamPaperCount(userId, examId) > 0)
			{
				status = "duplicate";
			}
			else
			{
				//���㱨������������
				int paperId = stemDao.registerExam(userId, examId);
				pay(userId,paperId);
				status = "success";
			}
		}
		json.put("status", status);
		return json.toString();
	}
/*	public int getexamidbyname(String examName){
		Exam exam = examDao.getExamInfoByName(examName);
		int examId=exam.getExamId();
		return examId;
	}*/
	public int getPaperId(int userId,int examId){
		return getPaperId(userId,examId);
	}
}
