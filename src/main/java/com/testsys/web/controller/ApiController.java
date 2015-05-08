package com.testsys.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.testsys.admin.controller.BaseController;
import com.testsys.web.service.StemService;

@Controller
@RequestMapping(value = "/api")
public class ApiController extends BaseController{

    @Autowired
    private StemService stemService;
    
    @RequestMapping(value = "/getchoiceresultlist.html")
    public void getChoiceResultList(HttpServletRequest request,HttpServletResponse response) {
    	PrintWriter out;
    	try
    	{
    		response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
    		String idcard=request.getParameter("idcard");
    		//User user = this.getSessionUser(request);
        	String json = stemService.getChoiceResultList(idcard);
    		out = response.getWriter();
			out.write(json);
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
    		JSONObject json=new JSONObject();
    		json.put("status", "exception");
    		try {
				out = response.getWriter();
				out.write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
    }
    
    
}
