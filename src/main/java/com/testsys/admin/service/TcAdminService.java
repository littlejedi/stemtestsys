package com.testsys.admin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testsys.constants.CommonConstant.paperStatus;
import com.testsys.web.bean.OpenAllo;
import com.testsys.web.bean.Paper;
import com.testsys.web.bean.PaperState;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Review;
import com.testsys.web.dao.StemDao;

@Service
public class TcAdminService {
	
	@Autowired
	private StemDao choiceDao;
	
	public void updatePaperStatus(int status,int paperId)
	{
		choiceDao.updateAction("es_exam_paper", " status = "+status, "paper_id = "+paperId);
	}

	public Review getReview(int reviewId)
	{
		return choiceDao.getReviewById(reviewId);
	}
	
	public boolean reviewPaper(int s,int t,int e,int m,int score,int reviewId)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		choiceDao.updateAction("es_open_review", "review_time = '"+sdf.format(date)+"',s="+s+",t="+t+",e="+e+",m="+m+",review_result="+score+",status=1", "review_id="+reviewId);
		Review review = choiceDao.getReviewById(reviewId);//int
		int unReviewCount = choiceDao.getMatchCount("es_open_review", "`status` = 0 and open_allo_id = "+review.getOpenAlloId());
		if(unReviewCount == 0)
		{
			double rs=0;
			double ss=0;
			double ts=0;
			double es=0;
			double ms=0;
			List<Review> reviewlist = choiceDao.getReviewList(review.getOpenAlloId());
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
			
			Paper paper = choiceDao.getPaperInfoByOpenAlloId(review.getOpenAlloId());
			choiceDao.updateAction("es_exam_result", "open_s="+(ss/count)+",open_t="+(ts/count)+",open_e="+(es/count)+",open_m="+(ms/count)+",open_score="+(rs/count), "result_id="+paper.getResultId());
			if(PaperState.stateTurnCheck(paperStatus.values()[paper.getStatus()], paperStatus.OPENREVIEWED))
				updatePaperStatus(paperStatus.OPENREVIEWED.ordinal(),paper.getPaperId());
		}
		return true;
	}
	public Open getOpenInfo(int openAlloId) {
		// TODO Auto-generated method stub
		OpenAllo oa = choiceDao.findOpenAllo(openAlloId);
		return choiceDao.getOpenQuestionById(Integer.valueOf(oa.getSelectedOpenId()));
	}
	
	public OpenAllo getOpenAlloById(int openAlloId) {
		return choiceDao.findOpenAllo(openAlloId);
	}

	public boolean checkBeginTime(int reviewId) {
		// TODO Auto-generated method stub
		Review review = choiceDao.getReviewById(reviewId);
		if(review.getBeginTime().equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			choiceDao.updateAction("es_open_review", "begin_time='"+sdf.format(date)+"'", "review_id="+reviewId);
			return true;
		}
		return false;
	}
}
