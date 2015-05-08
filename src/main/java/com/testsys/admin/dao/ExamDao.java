package com.testsys.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.testsys.admin.bean.Exam;
import com.testsys.admin.bean.Result;
import com.testsys.admin.bean.SignIn;
import com.testsys.constants.CommonConstant.paperStatus;

@Repository
public class ExamDao extends BaseDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
     
    public int getMatchPaper(int userId,int examId)
    {
    	String sqlStr = " SELECT count(*) FROM es_exam_paper "
                + " WHERE user_id =? and exam_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{userId, examId});
    }
    public int registerExam(int userId,int examId)
    {
    	String sqlStr="insert into es_exam_paper(user_id,exam_id) values (?,?);";
    	int[] types = new int[] { Types.INTEGER,Types.INTEGER };
    	int insertId = jdbcTemplate.update(sqlStr, new Object[]{userId,examId},types);
    	return insertId;
    }
    /**
     * 
     * @param begin
     * @param num
     * @return adminlist
     */
    public List<Exam> getExamList(int begin,int num)
    {
    	String sqlStr=" Select * from es_exam where delFlg = 0 order by exam_id desc limit ?,?;";
    	//@SuppressWarnings("unchecked")
		List<Exam> examList = jdbcTemplate.query(sqlStr, new Object[]{begin,num}, new RowMapper<Exam>()
    		{
			//@Autowired
			//private ExamDao examdao = new ExamDao();
    		public Exam mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Exam exam = new Exam();
    			exam.setExamId(rs.getInt("exam_id"));
    			exam.setAdminId(rs.getInt("admin_id"));
    			exam.setExamName(rs.getString("exam_name"));
    			exam.setExamSociety(rs.getString("exam_society"));
    			exam.setExamSubject(rs.getString("exam_subject"));
    			String date=rs.getString("exam_begin");
    			date=date.substring(0, date.length()-2);
    			exam.setExamBegin(date);//rs.getString("exam_begin"));
    			date=rs.getString("exam_end");
    			date=date.substring(0, date.length()-2);
            	exam.setExamEnd(date);//rs.getString("exam_end"));
            	exam.setExamLine(rs.getDouble("exam_line"));
            	exam.setNotice(rs.getString("notice"));
            	exam.setCreateTime(rs.getString("create_time"));
            	return exam;
    		}}); 
    	return examList;
    }
    public int getSignInNumbers(int examId)
    {
    	String sqlStr = " SELECT count(*) FROM es_exam_paper "
                + " WHERE exam_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{examId});
    }
    public int addExam(String examName,String examSociety,String examSubject,String examBegin,String examEnd,String notice,double examLine,int choiceNumber,int choiceTime,int adminId)
    {
    	return this.insertAction("es_exam", "exam_name,exam_society,exam_subject,exam_begin,exam_end,notice,exam_line,choice_number,choice_time,admin_id", "'"+examName+"','"+examSociety+"','"+examSubject+"','"+examBegin+"','"+examEnd+"','"+notice+"',"+examLine+","+choiceNumber+","+choiceTime+","+adminId+"");
    }
    
    /**
     * ����ɾ��
     * @param examId
     * @return
     */
    public boolean removeExam(int examId)
    {
    	String sqlStr="update es_exam set delFlg = 1 where exam_id = ?";
    	jdbcTemplate.update(sqlStr, new Object[]{examId});
    	return true;
    }
    
    public Exam getExamInfoById(int examId)
    {
    	String sqlStr = " SELECT * "
                + " FROM es_exam WHERE exam_id =? ";
        final Exam exam = new Exam();
        jdbcTemplate.query(sqlStr, new Object[]{examId},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	exam.setNotice(rs.getString("notice"));
                    	String date=rs.getString("exam_begin");
            			date=date.substring(0, date.length()-2);
            			exam.setExamBegin(date);//rs.getString("exam_begin"));
            			date=rs.getString("exam_end");
            			date=date.substring(0, date.length()-2);
                    	exam.setExamEnd(date);//rs.getString("exam_end"));
                    	exam.setExamLine(rs.getDouble("exam_line"));
                    	exam.setAdminId(rs.getInt("admin_id"));
                    	exam.setExamId(rs.getInt("exam_id"));
                    	exam.setChoiceNumber(rs.getInt("choice_number"));
                    	exam.setChoiceTime(rs.getInt("choice_time"));
                    	exam.setExamName(rs.getString("exam_name"));
                    	exam.setExamSociety(rs.getString("exam_society"));
                    	exam.setExamSubject(rs.getString("exam_subject"));
                    }
                });
        return exam;
    }
    
    public List<SignIn> getSignInList(int examId,int begin,int num)
    {
    	String sqlStr = "SELECT name, paper_id, es_user.user_id, exam_id, idcard, telephonenumber, course,status,create_time ";
    	sqlStr +=		"FROM es_user, es_exam_paper ";
    	sqlStr +=		"LEFT JOIN es_open_allocation ON es_open_allocation.open_allo_id = es_exam_paper.open_allo_id ";
    	sqlStr +=		"WHERE es_exam_paper.exam_id =? ";
    	sqlStr +=		"AND es_user.user_id = es_exam_paper.user_id ";
    	sqlStr +=		"LIMIT ?,?;";
    	//@SuppressWarnings("unchecked")
		List<SignIn> signInList = jdbcTemplate.query(sqlStr, new Object[]{examId,begin,num}, new RowMapper<SignIn>()
		{
			//@Autowired
			public SignIn mapRow(ResultSet rs, int rowNumber) throws SQLException {
				SignIn signin=new SignIn();
				signin.setExamId(rs.getInt("exam_id"));
				signin.setUserId(rs.getInt("user_id"));
				signin.setPaperId(rs.getInt("paper_id"));
				signin.setIdcard(rs.getString("idcard"));
				signin.setName(rs.getString("name"));
				signin.setStatus(rs.getInt("status"));
				if(rs.getInt("status") >= paperStatus.CHOICENOTPASS.ordinal() && rs.getInt("status") != paperStatus.CHOICEEXPIRED.ordinal())
					signin.setHasResult(1);
				else 
					signin.setHasResult(0);
				signin.setTelephoneNumber(rs.getString("telephonenumber"));
				String date=rs.getString("create_time");
				date=date.substring(0, date.length()-2);
				signin.setCreateTime(date);
				
				if(rs.getString("course") != null)
					signin.setCourse(rs.getString("course"));
				else
					signin.setCourse("U");
	        	return signin;
		}}); 
    	return signInList;
    }
    public List<SignIn> getTimeOutList(int begin,int num)
    {
    	String sqlStr = "SELECT name, paper_id, es_user.user_id, exam_id, idcard, telephonenumber, status,create_time ";
    	sqlStr +=		"FROM es_user, es_exam_paper ";
    	sqlStr +=		"WHERE es_exam_paper.status =5 ";
    	sqlStr +=		"AND es_user.user_id = es_exam_paper.user_id ";
    	sqlStr +=		"LIMIT ?,?;";
    	//@SuppressWarnings("unchecked")
		List<SignIn> signInList = jdbcTemplate.query(sqlStr, new Object[]{begin,num}, new RowMapper<SignIn>()
		{
			//@Autowired
			public SignIn mapRow(ResultSet rs, int rowNumber) throws SQLException {
				SignIn signin=new SignIn();
				signin.setExamId(rs.getInt("exam_id"));
				signin.setUserId(rs.getInt("user_id"));
				signin.setPaperId(rs.getInt("paper_id"));
				signin.setIdcard(rs.getString("idcard"));
				signin.setName(rs.getString("name"));
				signin.setStatus(rs.getInt("status"));
				if(rs.getInt("status") >= paperStatus.CHOICENOTPASS.ordinal() && rs.getInt("status") != paperStatus.CHOICEEXPIRED.ordinal())
					signin.setHasResult(1);
				else 
					signin.setHasResult(0);
				signin.setTelephoneNumber(rs.getString("telephonenumber"));
				String date=rs.getString("create_time");
				date=date.substring(0, date.length()-2);
				signin.setCreateTime(date);
				
	        	return signin;
		}}); 
    	return signInList;
    }
    public int getMatchExamCount() {
        return this.getMatchCount("es_exam", "delFlg = 0");
    }
    
    public Result getResult(int resultId)
	{
		String sqlStr="select *  from es_exam_result where result_id = ? ";
		final Result cr=new Result();
		jdbcTemplate.query(sqlStr, new Object[]{resultId},
				new RowCallbackHandler(){
					public void processRow(ResultSet rs) throws SQLException{
						cr.setChoices(rs.getDouble("choice_s"));
						cr.setChoicet(rs.getDouble("choice_t"));
						cr.setChoicee(rs.getDouble("choice_e"));
						cr.setChoicem(rs.getDouble("choice_m"));
						cr.setChoicescore(rs.getInt("choice_score"));
						cr.setStatus(rs.getInt("status"));
						String date=rs.getString("create_time");
		    			date=date.substring(0, date.length()-2);
		    			cr.setCreatetime(date);
		    			cr.setOpens(rs.getDouble("open_s"));
						cr.setOpent(rs.getDouble("open_t"));
						cr.setOpene(rs.getDouble("open_e"));
						cr.setOpenm(rs.getDouble("open_m"));
						cr.setOpenscore(rs.getDouble("open_score"));
					}
		});
		return cr;
	}
    public List<Result> getResultbyinfo(String society,String subject,String examname)
    {
    	String sqlStr="select* from test.es_exam_result where result_id=(select result_id from test.es_exam_paper where exam_id=(select exam_id from test.es_exam where exam_society=? and exam_subject=? and exam_name=?));";
    	       
    	       List<Result> resultList = jdbcTemplate.query(sqlStr, new Object[]{society,subject,examname}, new RowMapper<Result>()
    	    		   {
    	    	   public Result mapRow(ResultSet rs, int rowNumber) throws SQLException {
    	   			Result result = new Result();
    	   			result.setChoices(rs.getInt("result_id"));
    	   			result.setChoices(rs.getDouble("choice_s"));
    	   			result.setChoicet(rs.getDouble("choice_t"));
    	   			result.setChoicee(rs.getDouble("choice_e"));
    	   			result.setChoicem(rs.getDouble("choice_m"));
    	   			result.setChoicescore(rs.getInt("choice_score"));
    	   			result.setStatus(rs.getInt("status"));
    				String date=rs.getString("create_time");
        			date=date.substring(0, date.length()-2);
        			result.setCreatetime(date);
        			result.setOpens(rs.getDouble("open_s"));
        			result.setOpent(rs.getDouble("open_t"));
        			result.setOpene(rs.getDouble("open_e"));
        			result.setOpenm(rs.getDouble("open_m"));
        			result.setOpenscore(rs.getDouble("open_score"));
    	           	return result;
    	           	}});
    	return resultList;
    }
//改完发现没用到
public Exam getExamInfoByName(String examName,String nowstr)
{
	String sqlStr = " SELECT * "
            + " FROM es_exam WHERE exam_name =? and exam_begin>?";
    final Exam exam = new Exam();
    jdbcTemplate.query(sqlStr, new Object[]{examName,nowstr},
            new RowCallbackHandler() {
                public void processRow(ResultSet rs) throws SQLException {
                	exam.setNotice(rs.getString("notice"));
                	String date=rs.getString("exam_begin");
        			date=date.substring(0, date.length()-2);
        			exam.setExamBegin(date);//rs.getString("exam_begin"));
        			date=rs.getString("exam_end");
        			date=date.substring(0, date.length()-2);
                	exam.setExamEnd(date);//rs.getString("exam_end"));
                	exam.setExamLine(rs.getDouble("exam_line"));
                	exam.setAdminId(rs.getInt("admin_id"));
                	exam.setExamId(rs.getInt("exam_id"));
                	exam.setChoiceNumber(rs.getInt("choice_number"));
                	exam.setChoiceTime(rs.getInt("choice_time"));
                	exam.setExamName(rs.getString("exam_name"));
                	exam.setExamSociety(rs.getString("exam_society"));
                	exam.setExamSubject(rs.getString("exam_subject"));
                }
            });
    return exam;
}
}
