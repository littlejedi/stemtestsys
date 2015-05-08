package com.testsys.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.testsys.admin.bean.Admin;
import com.testsys.admin.bean.Review;
import com.testsys.admin.service.SuperAdminService;
import com.testsys.admin.service.TcAdminService;
import com.testsys.constants.CommonConstant;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.OpenAllo;

@Controller
@RequestMapping(value = "/tcadmin")
public class TeacherAdminController extends BaseController {

	@Autowired
	private TcAdminService tcService;

	@Autowired
	private SuperAdminService adminService;
	//private Object response;
	
	//显示原卷信息页面
	@RequestMapping(value = "/paper.html")
	public ModelAndView paperList(HttpServletRequest request) {
		try {
			int reviewId = Integer.valueOf(request.getParameter("reviewId"));
			com.testsys.web.bean.Review review = tcService
					.getReview(reviewId);
			Open open = tcService.getOpenInfo(review.getOpenAlloId());
			// review.getBeginTime();
			Admin admin = this.getSessionAdmin(request);
			if(admin.getAdminId() != review.getAdminId() || review.getStatus() != 0)
				throw new Exception("noauth");
			long remainingtime = 181;
			if (!review.getBeginTime().equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date begintime = sdf.parse(review.getBeginTime());
				long createseconds = begintime.getTime() / 1000;
				System.out.println(createseconds);
				Date now = new Date();
				long nowseconds = now.getTime() / 1000;
				long examlimit = 180;
				remainingtime = examlimit - (nowseconds - createseconds);
			}
			// System.out.println(review.getBeginTime());

			// int rt = 90;// 180 -(now-begin)
			request.setAttribute("reviewId", reviewId);
			request.setAttribute("remainingtime", remainingtime);
			request.setAttribute("open", open);
			return new ModelAndView("admin/stemEvalSystemPaper");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
		// int rt = 90;// 180 -(now-begin)
		// request.setAttribute("remainingtime", rt);

		// return new ModelAndView("admin/stemEvalSystemPaper");
	}

	@RequestMapping(value = "/setdownloadtime.html")
	public void setDownloadTime(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out;
		JSONObject json = new JSONObject();
		try {
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			int reviewId = Integer.valueOf(request.getParameter("reviewId"));
			Admin admin = this.getSessionAdmin(request);
			com.testsys.web.bean.Review review = tcService.getReview(reviewId);
			if(admin.getAdminId() != review.getAdminId() || review.getStatus() != 0)
				throw new Exception("noauth");
			if (tcService.checkBeginTime(reviewId)) {
				json.put("status", "empty");
			} else
				json.put("status", "exist");
			// String json=tcService.signIn(userId,examId);
			out = response.getWriter();
			out.write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("status", "error");
			try {
				out = response.getWriter();
				out.write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	@RequestMapping(value = "/reviewpaper.html")
	public ModelAndView reviewPaper(HttpServletRequest request) {
		try {
			int reviewId = Integer.valueOf(request.getParameter("reviewId"));
			Admin admin = this.getSessionAdmin(request);
			com.testsys.web.bean.Review review = tcService.getReview(reviewId);
			if(admin.getAdminId() != review.getAdminId() || review.getStatus() != 0)
				throw new Exception("noauth");
			int s = Integer.valueOf(request.getParameter("s"));
			int t = Integer.valueOf(request.getParameter("t"));
			int e = Integer.valueOf(request.getParameter("e"));
			int m = Integer.valueOf(request.getParameter("m"));
			int score = Integer.valueOf(request.getParameter("review_result"));
			tcService.reviewPaper(s, t, e, m, score, reviewId);
			return new ModelAndView(new RedirectView("reviewlist.html"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
		// int rt = 90;// 180 -(now-begin)
		// request.setAttribute("remainingtime", rt);

		// return new ModelAndView("admin/stemEvalSystemPaper");
	}

	@RequestMapping(value = "/reviewlist.html")
	public ModelAndView reviewList(HttpServletRequest request) {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");

		try {
			//Admin admin = this.getSessionAdmin(request);
			
			
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.valueOf(request.getParameter("page"));
			int listrows = CommonConstant.LISTROWS;

			Admin admin = this.getSessionAdmin(request);

			List<Review> reviewlist = adminService.getReviewList(admin.getAdminId(), page,listrows);
			request.setAttribute("admin", admin);
			request.setAttribute("reviewlist", reviewlist);
			return new ModelAndView("admin/stemEvalSystemPaperList");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("admin/stemEvalSystemError");
		}
	}

	@RequestMapping(value = "/download.html")
	public void paperDownload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
			
			
			int reviewId = Integer.valueOf(request.getParameter("reviewId"));
			// Paper paper = tcService.getPaperInfo(paperId);
			com.testsys.web.bean.Review review = tcService.getReview(reviewId);
			OpenAllo oa = tcService.getOpenAlloById(review.getOpenAlloId());
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			java.io.BufferedInputStream bis = null;
			java.io.BufferedOutputStream bos = null;
	
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("answer");
			String fileName = oa.getAnswerFileSrc();// "a.txt";
			String downLoadPath = ctxPath + "/" + fileName;
	
			System.out.println(downLoadPath);

		try {
				
			Admin admin = this.getSessionAdmin(request);
			if(admin.getAdminId() != review.getAdminId() || review.getStatus() != 0)
				throw new Exception("noauth");
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));

			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(downLoadPath));

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];

			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {

				bos.write(buff, 0, bytesRead);
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (bis != null)

				bis.close();

			if (bos != null)

				bos.close();
		}
	}

}
