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
import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.Review;

@Repository
public class AdminDao extends BaseDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int getMatchUserCount(String account, String password) {
        String sqlStr = " SELECT count(*) FROM es_admin "
                + " WHERE account =? and password=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{account, password});
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
                + " WHERE delFlg = 0 and admin_id=? ";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{adminId});
    }
    public int getOpenNumbers(int adminId)
    {
    	String sqlStr = " SELECT count(*) FROM es_open_question "
                + " WHERE delFlg = 0 and admin_id=? ";
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
    	//String sqlStr="update es_admin set delFlg = 1 where admin_id in (?)";
    	String sqlStr = "delete from es_admin where admin_id in (?)";
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
    	//String sqlStr="update es_admin set delFlg = 1 where admin_id = ?";
    	String sqlStr = "delete from es_admin where admin_id = ?";
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
    public int addAdmin(String account,int role,String name,String password,String course)
    {
    	String sqlStr="insert into es_admin(account,password,name,role,course) values (?,?,?,?,?);";
    	int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,Types.VARCHAR };
    	int insertId = jdbcTemplate.update(sqlStr, new Object[]{account,password,name,role,course},types);
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
                + " FROM es_admin WHERE account =? ";
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
    public int getMatchAdminCount(int role) {
        return this.getMatchCount("es_admin", "delFlg = 0 and role = " + role);
    }
}
