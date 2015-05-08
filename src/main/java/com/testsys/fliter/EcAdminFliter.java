package com.testsys.fliter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.testsys.admin.bean.Admin;

public class EcAdminFliter extends AdminFliter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {  
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");  
        }  
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;  
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;  
        Admin admin = this.getSessionAdmin(httpRequest);
        String  uri = httpRequest.getRequestURI();
    	uri = uri.substring(0, uri.lastIndexOf("ecadmin"));
        if(admin == null)
        {
        	httpResponse.sendRedirect(uri + "adminlogin/login.html");
        }
        else if(admin.getRole() != 2)
        {
        	httpResponse.sendRedirect(uri + this.roleRedirect(admin.getRole()));
        }
        else
        {
        	httpRequest.setAttribute("adminname", admin.getName());
        	filterChain.doFilter(httpRequest, httpResponse);  
            return;
        }
       
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

