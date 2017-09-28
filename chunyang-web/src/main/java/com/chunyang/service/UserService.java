package com.chunyang.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chunyang.dao.UserDao;

@Service
public class UserService {
  @Autowired
  private UserDao userDao;
  
  @Value("${chunyang-test.num}")
 	private String testnum;
  
  public boolean hasMatchUser(String userName,String password){
	  Log log = LogFactory.getLog(UserService.class);
	  log.info(testnum);
	  int matchCount = userDao.getMatchCount(userName, password);
	  return matchCount>0;
  }
  
}
