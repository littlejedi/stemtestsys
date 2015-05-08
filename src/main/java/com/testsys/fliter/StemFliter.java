package com.testsys.fliter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.testsys.admin.bean.User;
import com.testsys.constants.CommonConstant;

public class StemFliter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;  
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;  
		// TODO Auto-generated method stub
		//try{
			String uri = httpRequest.getRequestURI();
			//System.out.println(uri);
			int htmlindex = uri.indexOf(".html");
			if(htmlindex == -1)
			{
	    		filterChain.doFilter(httpRequest, httpResponse);
	    		return;
	    	}
	       /* String  uri = httpRequest.getRequestURI();
	    	uri = uri.substring(uri.lastIndexOf(".")+1);
	    	//System.out.println(uri);
	    	if(!uri.equals("html"))
	    	{
	    		filterChain.doFilter(httpRequest, httpResponse);
	    		return;
	    	}*/
	    	uri = httpRequest.getRequestURI();
	    	uri = uri.substring(uri.lastIndexOf("stem")+5);
	    	if(uri.equals("registeruser.html"))
	    	{
	    		filterChain.doFilter(httpRequest, httpResponse);
	    		return;
	    	}
	    	//check there is a cookie
	    	if(this.userCookieCheck(httpRequest))
	    	{
	    		String cookie = getCookieValue(CommonConstant.USER_CONTEXT, httpRequest);
	    		JSONObject json = JSONObject.fromObject(cookie);
	    		int userId = json.getInt("userId");
	    		String username = "";
	    		if(json.get("username") != null)
	    			username = /*(String) */json.get("username").toString();
	    		String realname = "";
	    		if(json.get("realName") != null)
	    			realname = /*(String)*/ json.get("realName").toString();
	    		//if(realname == null)
	    		//	realname = "";
	    		realname = URLDecoder.decode(realname, "UTF-8");
	    		String idcard = "";
	    		if(json.get("nationalId") != null)
	    			idcard = /*(String)*/ json.get("nationalId").toString();
	    		String phone = "";
	    		if(json.get("phone") != null)
	    			phone = json.get("phone").toString();
	    				//URLEncoder.decode(Strings.nullToEmpty(realname),"UTF-8")
	    		User user = (User) httpRequest.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	    		if(user == null || user.getUserId() != userId || !user.getUserName().equals(username) || !user.getIdcard().equals(idcard) || !user.getRealname().equals(realname) || !user.getTelephone().equals(phone))
	    		{
	    			//System.out.println("userId:"+userId+",username:"+username);
	    			User user2 = new User();
	    			user2.setUserId(userId);
	    			user2.setUserName(username);
	    			user2.setIdcard(idcard);
	    			user2.setRealname(realname);
	    			user2.setTelephone(phone);
	    			
	    			httpRequest.getSession().setAttribute(CommonConstant.USER_CONTEXT, user2);
	    			uri = httpRequest.getRequestURI();
	    	    	uri = uri.substring(uri.lastIndexOf("stem")+5);
	    			httpRequest.setAttribute("url", uri);
	    			httpRequest.getRequestDispatcher("/stem/registeruser.html").forward(httpRequest,httpResponse);
	    		}
	    		else
	    		{
	    			filterChain.doFilter(httpRequest, httpResponse);
	    		}
	    	}
	    	else
	    	{
	    		httpRequest.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
	    		uri = httpRequest.getRequestURI();
	    		// For test purposes, we can manually set cookies here
	    	    this.setCookie(httpResponse);
	    		httpResponse.sendRedirect(uri);
	    		//httpResponse.sendRedirect("/login.html?redirectURL="+uri);
	    	}
		/*}
		catch(Exception e)
		{
			e.printStackTrace();
			httpResponse.sendRedirect("/error.html");
		}*/
    	
    	/*
        User user = (User) httpRequest.getSession().getAttribute(CommonConstant.USER_CONTEXT);
        if(user == null)// && !this.userCookieCheck(httpRequest))
        {
        	if(this.userCookieCheck(httpRequest))
        	{
        	//	System.out.println("hascookie");
    			String cookie = getCookieValue(CommonConstant.USER_CONTEXT, httpRequest);
    			//System.out.println(cookie);
    			JSONObject json = JSONObject.fromObject(cookie);
    			User user2 = new User();
    			//json.g
    			//Integer.valueOf((String) json.get("userId"))
    			user2.setUserId(json.getInt("userId"));
    			user2.setUserName((String) json.get("username"));
    			httpRequest.getSession().setAttribute(CommonConstant.USER_CONTEXT, user2);
    			uri = httpRequest.getRequestURI();
    	    	uri = uri.substring(uri.lastIndexOf("stem")+5);
    			httpRequest.setAttribute("url", uri);
    			httpRequest.getRequestDispatcher("/stem/registeruser.html").forward(httpRequest,httpResponse);
        	}
        	else
        	{
        		//System.out.println("nocookie");
        		uri = httpRequest.getRequestURI(); */
        		/*
        		this.setCookie(httpResponse);
        		uri = httpRequest.getRequestURI();
        		httpResponse.sendRedirect(uri);*/
        		//System.out.println(uri);
        		/*httpResponse.sendRedirect("/login.html?redirectURL="+uri);//java.net.URLEncoder.encode(uri,"utf-8"));
        	}
        }
        else
        	filterChain.doFilter(httpRequest, httpResponse);*/
	}
    
	public void setCookie(HttpServletResponse response)
    {
    	JSONObject json = new JSONObject();
    	json.put("userId", 863);
    	json.put("username", "220203198810144514");
    	//String a = null;
    	json.put("nationalId", "11990");
    	json.put("realName", "durpersi");
    	json.put("phone", "18516200724");
    	Cookie c = new Cookie(CommonConstant.USER_CONTEXT,json.toString());
    	c.setVersion(1);
        response.addCookie(c);
    }
	
	protected boolean userCookieCheck(HttpServletRequest request) {
		boolean hasCookie = false;
		try
		{
			Cookie[] cookieList = request.getCookies(); 
			if(cookieList != null)
			{
				 for(Cookie cookie: cookieList){
					 if(CommonConstant.USER_CONTEXT.equals(cookie.getName())){
						 hasCookie = true; 
						 break; 
					 } 
				 } 
			}
			return hasCookie;
			//System.out.println(2);
			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static String getCookieValue(String name, HttpServletRequest request) {
		try {
			if (name == null)
				return "";
			String allCookieStr = request.getHeader("Cookie");
			//System.out.println("1"+allCookieStr);
			if (allCookieStr != null) {
				name = name + "=";
				int begin = allCookieStr.indexOf(name);
				if (begin >= 0) {
					int end = allCookieStr.indexOf(";", begin);
					if (end >= 0) {
						String tmp = allCookieStr.substring(
								begin + 1 + name.length(), end - 1);
						tmp = tmp.replace("\\\"", "\"");
						// tmp.r
						return tmp;
					} else {
						String result = allCookieStr.substring(begin + name.length());
						int b = result.indexOf("{");
						int e = result.lastIndexOf("}");
						if(b == -1)
							b = 0;
						if(e == -1)
							e = result.length();
						result = result.substring(b,e+1);
						result = result.replace("\\\"", "\"");
						//System.out.println(result);
						return result;//allCookieStr.substring(begin + name.length()+ 1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
