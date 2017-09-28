package com.maven.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
public class MyFilter implements Filter {

	public void destroy() {
		// TODO 销毁拦截器的资源
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		arg2.doFilter(arg0, arg1);
		/*// TODO 对访问的请求进行处理
		*//**
		 * 这里可以通过 ServletRequest 对请求进行处理
		 *//*
		HttpServletRequest res = (HttpServletRequest) arg0;
		HttpServletResponse rep = (HttpServletResponse) arg1;
		String path = res.getServletPath();//实际访问的路径
		//获取访问的地址
		*//**
		 * res.getScheme() 获取访问的协议 http https
		 * res.getServerName() 获取访问的项目名称
		 * res.getServerPort() 获取访问的端口
		 * res.getContextPath();//获取项目名称
		 * res.getServletPath();//实际访问的路径
		 *//*
		String basePath = res.getScheme()+"://"+res.getServerName()+"："+res.getServerPort()+"/"+res.getContextPath();//获取项目名称;
		//获取session
		HttpSession httpSession = res.getSession();
		if(StringUtils.isEmpty(httpSession.getAttribute("userName"))){//判断用户是否已经登录，登陆后会将用户的可见信息加入到session中
			rep.sendRedirect(basePath+path);//没有登录 跳转到登录界面
		}else{//用户已经登录过系统了
			//if(path.contains(".css|.png|.js")){
			//	arg2.doFilter(arg0, arg1);
			//}
			arg2.doFilter(arg0, arg1);
		}*/
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO 初始化操作
		String param = arg0.getInitParameter("param-name");//获取到filter中的init-param的参数
		System.out.println(param);
	}
}
