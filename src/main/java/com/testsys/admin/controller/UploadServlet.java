package com.testsys.admin.controller;

import java.text.SimpleDateFormat;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

import com.testsys.constants.CommonConstant;
import com.web.ZipDecompressing;


public class UploadServlet extends HttpServlet {
  private String filePath; //����ϴ��ļ���Ŀ¼
  private String tempFilePath; //�����ʱ�ļ���Ŀ¼
  private String imagePath;
  private String excelPath;
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    filePath=config.getInitParameter("filePath");
    tempFilePath=config.getInitParameter("tempFilePath");
    imagePath=config.getInitParameter("imagePath");
    excelPath=config.getInitParameter("excelPath");
    
    filePath=CommonConstant.UPLOAD_FOLDER+filePath;//getServletContext().getRealPath(filePath);//+filePath;
    tempFilePath=getServletContext().getRealPath(tempFilePath);//+tempFilePath;
    imagePath=CommonConstant.UPLOAD_FOLDER+imagePath;//getServletContext().getRealPath(imagePath);//
    excelPath=CommonConstant.UPLOAD_FOLDER+excelPath;//getServletContext().getRealPath(excelPath);//
  }
  public void doPost(HttpServletRequest request,HttpServletResponse response)
         throws ServletException, IOException {
    response.setContentType("text/plain");
    try{
      DiskFileItemFactory factory = new DiskFileItemFactory();
      factory.setSizeThreshold(20*1024*1024); 
      factory.setRepository(new File(tempFilePath));
      ServletFileUpload upload = new ServletFileUpload(factory);  
      upload.setSizeMax(20*1024*1024); 
      request.setCharacterEncoding("UTF-8");
      
      PrintWriter out = response.getWriter();
      out.println("success");
      out.println(tempFilePath);
      Date a=new Date();
  	  out.println(a.toString()+"/"+a.toLocaleString());
      List  items = upload.parseRequest(request);    
      Iterator iter = items.iterator();
      while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        out.println(item.getFieldName());
        if(item.isFormField()) {
        }else{     	
          String attr = processUploadedFile(item); 
          request.setAttribute(item.getFieldName()+"file", attr);
        }
      }
      request.setAttribute("aa", "aaa");
      request.getRequestDispatcher("/ecadmin/readexcel.html").forward(request,response);
      
      
    }catch(Exception e){
       throw new ServletException(e);
    }
  }
  private String processUploadedFile(FileItem item)throws Exception{	  
    String filename=item.getName();
    int index=filename.lastIndexOf("\\");
    filename=filename.substring(index+1,filename.length());
    long fileSize=item.getSize();
    if(filename.equals("")&&fileSize==0)
    	return "";
    
    String name = item.getFieldName(); 
    
    if(name.equals("image"))
    {
    	File zipdir=new File(imagePath + "/zip/");
    	if(!zipdir.exists())
    		zipdir.mkdirs();
    	File uploadedFile = new File(imagePath + "/zip/" + filename);
    	item.write(uploadedFile);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date=new Date();
		String folder=sdf.format(date);
    	File unzipdir=new File(imagePath + "/"+folder+"/");
    	if(!unzipdir.exists())
    		unzipdir.mkdirs();
    	this.unzipimage(imagePath + "/zip/" + filename, unzipdir.getAbsolutePath()+"/");
    	return folder;
    }
    else if(name.equals("excel"))
    {
    	File exceldir = new File(excelPath+"/");
    	if(!exceldir.exists())
    		exceldir.mkdirs();
    	File uploadedFile = new File(excelPath+"/"+filename);
    	item.write(uploadedFile);
		return excelPath+"/"+filename;
    }
    return "";
  }
  
  private void unzipimage(String imagepath,String dstdir)
  {
	  ZipDecompressing book=new ZipDecompressing();
  	  File zipfile=new File(imagepath);
  	  book.decompressing(zipfile,dstdir);
  }
}
