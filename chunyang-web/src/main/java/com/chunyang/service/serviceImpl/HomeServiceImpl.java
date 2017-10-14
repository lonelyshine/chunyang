package com.chunyang.service.serviceImpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chunyang.mapper.HomeMapper;
import com.chunyang.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{
    @Resource
    private HomeMapper homeMapper;
	
	@Override
	public List<HashMap<String, Object>> getUserInfo(String username) {
		System.out.println(username);
		System.out.println(homeMapper.getUserInfo(username).isEmpty());
		return homeMapper.getUserInfo(username);
	}

}
