package com.testsys.admin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class BaseDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    
    public int getMatchCount(String tablename, String condition) {
        String sqlStr = " SELECT count(*) FROM " + tablename + " where " + condition;
        return jdbcTemplate.queryForInt(sqlStr);
    }
    /**
     * ����ɾ��
     * @param examId
     * @return
     */
    public boolean removeAction(String tablename,String condition)
    {
    	String sqlStr="update " + tablename + " set delFlg = 1 where " + condition;
    	jdbcTemplate.update(sqlStr);
    	return true;
    }
    public int insertAction(String tablename,String fields,String values)
    {
    	final String sqlStr="insert into "+tablename+" ( " + fields + " ) values ( " + values + " ) ;";
    	System.out.print(sqlStr);
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

    	//��� ���� id
    	int insertId = keyHolder.getKey().intValue();
    	return insertId;
    }
    public void updateAction(String tablename,String update,String condition)
    {
    	String sqlStr=" update " + tablename + " set "+update+" where " + condition+" ";
    	jdbcTemplate.update(sqlStr);
    }
   
}
