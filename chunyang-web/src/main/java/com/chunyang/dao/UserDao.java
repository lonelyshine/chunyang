package com.chunyang.dao;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.chunyang.domain.User;

@Repository //注解定义一个dao
public class UserDao {

	@Autowired //注入一个bean
	private JdbcTemplate jdbcTemplate;
	
	public int getMatchCount(String userName,String password){
		String sqlStr ="SELECT COUNT(*) FROM t_user WHERE user_name=? and password=?";
		return jdbcTemplate.queryForInt(sqlStr, new Object[]{userName,password});
	}
	final User user = new User();
	public User findUserByUserName(String userName){
		String sqlStr="select user_id,user_name,credits from t_user where user_name=?";
		jdbcTemplate.query(sqlStr, new Object[]{userName}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("userName"));
				user.setCredits(rs.getInt("credits"));
			}
		});
		return user;
	}
	public void updateLoginInfo(User user){
		String sqlStr ="update t_user set last_visit=?,last_ip=?,credits=? where user_id=?";
		jdbcTemplate.update(sqlStr, new Object[]{user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});
	}
}
