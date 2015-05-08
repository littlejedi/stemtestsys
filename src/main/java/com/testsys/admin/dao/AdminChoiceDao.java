/*<Context   path="/manager"   docBase="manager"     
                    debug="1"   reloadable="true"/>  */
package com.testsys.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;

import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.Review;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Option;

@Repository
public class AdminChoiceDao extends BaseDao{
    
    public int getMatchChoiceCount(int adminId,String condition) {
        return this.getMatchCount("es_choice_question", "delFlg = 0 and admin_id = " + adminId + (condition.equals("") ? " ":(" and "+condition)) );
    }
    
    /**
     * ����ɾ��
     * @param choiceId
     * @return
     */
    public boolean removeChoice(int choiceId)
    {
    	return this.removeAction("es_choice_question", "choice_id = " + choiceId);
    }
    
    /**
     * 
     * @param begin
     * @param num
     * @return adminlist
     */
    public List<Admin> getAdminList(int begin,int num,int role)
    {
    	String sqlStr=" Select * from es_admin where role = ? and delFlg = 0 order by admin_id desc limit ?,?;";
    	//@SuppressWarnings("unchecked")
		List<Admin> adminList = jdbcTemplate.query(sqlStr, new Object[]{role,begin,num}, new RowMapper<Admin>()
    	{
    		public Admin mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Admin admin = new Admin();
    			admin.setAdminId(rs.getInt("admin_id"));
            	admin.setAccount(rs.getString("account"));
            	admin.setRole(rs.getInt("role"));
            	admin.setPassword(rs.getString("password"));
            	if(rs.getString("course") != null)
            		admin.setCourse(rs.getString("course"));
            	admin.setName(rs.getString("name"));
            	String date=rs.getString("create_time");
    			date=date.substring(0, date.length()-2);
    			admin.setCreateTime(date);
            	return admin;
    		}}); 
    	return adminList;
    }
    
    public int getChoiceNumbers(int adminId)
    {
    	String sqlStr = " SELECT count(*) FROM es_choice_question "
                + " WHERE admin_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{adminId});
    }
    public int getOpenNumbers(int adminId)
    {
    	String sqlStr = " SELECT count(*) FROM es_open_question "
                + " WHERE admin_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{adminId});
    }
    public int getPaperNumbers(int adminId)
    {
    	String sqlStr = " SELECT count(*) FROM es_open_review "
                + " WHERE admin_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{adminId});
    }
    
    public int getSignInNumbers(int examId)
    {
    	String sqlStr = " SELECT count(*) FROM es_exam_paper "
                + " WHERE exam_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{examId});
    }
    
    /**
     * 
     * @param adminId
     * @return
     */
    public Admin findUserByAdminId(final int adminId) {
        String sqlStr = " SELECT * "
                + " FROM es_admin WHERE admin_id =? and delFlg = 0";
        final Admin admin = new Admin();
        jdbcTemplate.query(sqlStr, new Object[]{adminId},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	admin.setAdminId(rs.getInt("admin_id"));
                    	admin.setAccount(rs.getString("account"));
                    	admin.setRole(rs.getInt("role"));
                    	admin.setPassword(rs.getString("password"));
                    	admin.setName(rs.getString("name"));
                    }
                });
        return admin;
    }
    
    /**
     * ����ɾ��
     * @param adminIdList
     * @return
     */
    public boolean removeAdmin(String adminIdList)
    {
    	String sqlStr="update es_admin set delFlg = 1 where admin_id in (?)";
    	jdbcTemplate.update(sqlStr, new Object[]{adminIdList});
    	return true;
    }
    /**
     * ����ɾ��
     * @param adminId
     * @return
     */
    public boolean removeAdmin(int adminId)
    {
    	String sqlStr="update es_admin set delFlg = 1 where admin_id = ?";
    	jdbcTemplate.update(sqlStr, new Object[]{adminId});
    	return true;
    }
    /**
     * 
     * @param account
     * @param role
     * @param name
     * @param password
     * @param course
     * @return
     */
    public int addChoice(String fields,String values)
    {
    	final String sqlStr="insert into es_choice_question ("+fields+") values ("+values+");";
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = arg0.prepareStatement(sqlStr,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                return ps;
			}
             
        }, keyHolder);

    	int insertId = keyHolder.getKey().intValue();
    	return insertId;
    }
    public int addOption(String fields,String values)
    {
    	final String sqlStr="insert into es_choice_option ("+fields+") values ("+values+");";
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = arg0.prepareStatement(sqlStr,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                return ps;
			}
             
        }, keyHolder);

    	int insertId = keyHolder.getKey().intValue();
    	return insertId;
    }
    
    /**
     * 
     * @param adminId
     * @param password
     * @return
     */
    public int modifyPassAdmin(String adminId,String password)
    {
    	String sqlStr="update es_admin set password = ? where admin_id = ?;";
    	jdbcTemplate.update(sqlStr, new Object[]{adminId,password});
    	return 1;
    }
    
    public Admin findUserByUserName(final String account) {
        String sqlStr = " SELECT admin_id,name,role,account,password "
                + " FROM admin WHERE account =? ";
        final Admin admin = new Admin();
        jdbcTemplate.query(sqlStr, new Object[]{account},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	admin.setAdminId(rs.getInt("admin_id"));
                    	admin.setAccount(account);
                    	admin.setRole(rs.getInt("role"));
                    	admin.setPassword(rs.getString("password"));
                    	admin.setName(rs.getString("name"));
                    }
                });
        return admin;
    }
    
    public List<Review> getReviewList(int adminId,int begin,int num)
    {
    	String sqlStr=" select e.exam_id,u.user_id,u.name,e.exam_begin,e.exam_end,o.status,o.review_id " +
    			"from es_exam as e,es_user as u,es_open_review as o,es_exam_paper as ep " +
    			"where e.exam_id=ep.exam_id and u.user_id=ep.user_id and ep.open_allo_id=o.open_allo_id and o.admin_id=? order by o.review_id desc limit ?,?;";
    	//@SuppressWarnings("unchecked")
		List<Review> reviewList = jdbcTemplate.query(sqlStr, new Object[]{adminId,begin,num}, new RowMapper<Review>()
    		{
    		public Review mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Review review = new Review();
    			String date=rs.getString("exam_begin");
    			date=date.substring(0, date.length()-2);
    			review.setExamBegin(date);

    			date=rs.getString("exam_end");
    			date=date.substring(0, date.length()-2);
    			review.setExamEnd(date);
    			review.setExamId(rs.getInt("exam_id"));
    			review.setUserId(rs.getInt("user_id"));
    			review.setReviewId(rs.getInt("review_id"));
    			review.setName(rs.getString("name"));
    			review.setStatus(rs.getInt("status"));
    			if(rs.getInt("status") == 2)
    				review.setHasResult(1);
				else 
					review.setHasResult(0);
            	return review;
    		}}); 
    	return reviewList;
    }
    public List<Choice> getChoiceList(int begin,int num,int adminId,String condition)
    {
    	String sqlStr=" Select * from es_choice_question where admin_id = ? and delFlg = 0 "+ (condition.equals("") ? "":(" and "+condition)) +" order by choice_id desc limit ?,?;";
    	//@SuppressWarnings("unchecked")
		List<Choice> choiceList = jdbcTemplate.query(sqlStr, new Object[]{adminId,begin,num}, new RowMapper<Choice>()
    		{
    		public Choice mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Choice c = new Choice();
    			
    			c.setChoiceId(rs.getInt("choice_id"));
				c.setCourse(rs.getString("course"));
				c.setType(rs.getString("type"));
				
				String date=rs.getString("create_time");
				date=date.substring(0,date.length()-2);
				c.setCreateTime(date);
				
				String title = rs.getString("title");
				if(title==null)
					title = "";
				c.setTitle(title.substring(0, (title.length() > 30) ? 30 : title.length()));
				c.setDifficulty(rs.getInt("difficulty"));
				c.setExaminer(rs.getString("examiner"));
				c.setVerifier(rs.getString("verifier"));
				
				c.setS(rs.getInt("S"));
				c.setT(rs.getInt("T"));
				c.setE(rs.getInt("E"));
				c.setM(rs.getInt("M"));
            	return c;
    		}}); 
    	return choiceList;
    }
    
    
    public List<Open> getOpenList(int begin,int num,int adminId,String condition)
    {
    	String sqlStr=" Select * from es_open_question where admin_id = ? and delFlg = 0 "+ (condition.equals("") ? "":(" and "+condition)) +" order by open_id desc limit ?,?;";
    	//@SuppressWarnings("unchecked")
		List<Open> openList = jdbcTemplate.query(sqlStr, new Object[]{adminId,begin,num}, new RowMapper<Open>()
    		{
    		public Open mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Open c = new Open();
    			
    			c.setOpenId(rs.getInt("open_id"));
				c.setCourse(rs.getString("course"));
				
				String date=rs.getString("create_time");
				date=date.substring(0,date.length()-2);
				c.setCreateTime(date);
				
				String title = rs.getString("title");
				c.setTitle(title.substring(0, (title.length() > 30) ? 30 : title.length()));
				c.setImg(rs.getString("img"));
				c.setDifficulty(rs.getInt("difficulty"));
				c.setExaminer(rs.getString("examiner"));
				c.setVerifier(rs.getString("verifier"));
            	return c;
    		}}); 
    	return openList;
    }
    
    
    public Choice findChoiceInfoById(int choiceId)
	{
		String sqlStr="select * " +
				"from es_choice_question as ec " +
				"where ec.delFlg = 0 and ec.choice_id = ?";
		final Choice c=new Choice();
		jdbcTemplate.query(sqlStr, new Object[]{choiceId},
				new RowCallbackHandler(){
					public void processRow(ResultSet rs) throws SQLException{
						
						c.setChoiceId(rs.getInt("choice_id"));
						c.setCourse(rs.getString("course"));
						c.setType(rs.getString("type"));
						
						String date=rs.getString("create_time");
						date=date.substring(0,date.length()-2);
						c.setCreateTime(date);
						String title = rs.getString("title");
						if(title == null)
							c.setTitle("");
						else
							c.setTitle(title);
						c.setImg(rs.getString("img"));
						
						c.setS(rs.getInt("S"));
						c.setT(rs.getInt("T"));
						c.setE(rs.getInt("E"));
						c.setM(rs.getInt("M"));
					}
		});
		return c;
	}
    public List<Option> findOptionListByChoiceId(int choiceId)
    {
    	String sqlStr=" Select * from es_choice_option where choice_id = ?";
    	//@SuppressWarnings("unchecked")
		List<Option> optionList = jdbcTemplate.query(sqlStr, new Object[]{choiceId}, new RowMapper<Option>()
    		{
    		public Option mapRow(ResultSet rs, int rowNumber) throws SQLException {
    			Option op=new Option();
    			op.setChoiceId(rs.getInt("choice_id"));
    			String content = rs.getString("option_content");
    			if(content == null)
    				op.setOptionContent("");
    			else
    				op.setOptionContent(content);
    			op.setOptionId(rs.getInt("option_id"));
    			op.setOptionImg(rs.getString("option_img"));
    			op.setIdentifier(rs.getString("option_identifier"));
    			op.setOptionWeight(rs.getInt("option_weight"));
    			return op;
    		}}); 
    	return optionList;
    }

	public int getMatchOpenCount(int adminId, String condition) {
		// TODO Auto-generated method stub
		return this.getMatchCount("es_open_question", "delFlg = 0 and admin_id = " + adminId + (condition.equals("") ? " ":(" and "+condition)) );
	}

	public boolean removeOpen(int openId) {
		// TODO Auto-generated method stub
		return this.removeAction("es_open_question", "open_id = " + openId);
	}

	public Open getOpenInfoById(int openId) {
			// TODO Auto-generated method stub
			String sqlStr="select * " +
					" from es_open_question as oq " +
					" where oq.open_id = ? ";
			final Open open=new Open();
			jdbcTemplate.query(sqlStr, new Object[]{openId},
					new RowCallbackHandler(){
						public void processRow(ResultSet rs) throws SQLException{
							open.setTitle(rs.getString("title"));
							open.setOpenId(rs.getInt("open_id"));
							open.setImg(rs.getString("img"));
							open.setAs_example(rs.getString("as_example"));
							open.setDescription(rs.getString("description"));
							open.setTest_point(rs.getString("test_point"));
						}
			});
			return open;
		}
}
