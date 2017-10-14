package com.chunyang.service;

import java.util.HashMap;
import java.util.List;

/**
 * 小程序首页服务接口
 * @author qinxuegang
 *
 */
public interface HomeService {
	
	/**
	 * 获取用户信息
	 * @return 用户信息
	 */
	public List<HashMap<String,Object>> getUserInfo(String username);
}
