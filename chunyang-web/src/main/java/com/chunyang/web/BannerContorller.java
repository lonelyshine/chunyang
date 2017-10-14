package com.chunyang.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chunyang.service.HomeService;
import com.chunyang.util.BaseController;

/**
 * 小程序中首页的轮播图相关展示内容
 * @author qinxuegang
 */
@Controller
@RequestMapping(value="/test")
public class BannerContorller extends BaseController{
	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value="/test11",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String banner(@Param(value="username") String username){
		return responseArraySuccess(homeService.getUserInfo(username));
	}
}
