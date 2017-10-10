package com.chunyang.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  
  @Value("${chunyang-test.num}")
 	private String testnum;
  
  public boolean hasMatchUser(String userName,String password){
	  Log log = LogFactory.getLog(UserService.class);
	  log.info(testnum);
	 
	  return false;
  }
  
}
