package com.testsys.constants;

/**
 *
 *<br><b></b>
 *<pre>|</pre>
 *@see
 *@since
 */
public class CommonConstant
{
   /**
    * 
    */
   public static final String USER_CONTEXT = "userContext";
   public static final String ADMIN_CONTEXT = "ADMIN_CONTEXT";
   public static final String UPLOAD_FOLDER = "/root/java/deployment/uploads/testsys/";////"D:/stem/upload/";//
   /**
    * 
    */
   public static final String LOGIN_TO_URL = "toUrl";
   
   /**
    * 
    */
   public static int PAGE_SIZE = 3;
   /**
    * listrows of one page 
    */
   public static final int LISTROWS = 20;
   /**
    * status meaning
    */
   public static final String[] statusArr={
	   "已报名，未支付",
	   "已支付，未开始",
	   "选择题考试进行中",
	   "选择题考试未通过",
	   "选择题考试已通过",
	   "选择题考试超时",
	   "开放题考试已开始",
	   "已选择开放题，考试进行中",
	   "开放题已提交",
	   "阅卷老师已批阅，考试结束",
	   "开放题考试超时"
	  };
   public static enum paperStatus{
	   SIGNIN,//已报名，未支付
	   HASPAY,//已支付，未开始
	   CHOICEING,//选择题考试进行中
	   CHOICENOTPASS,//选择题未通过
	   CHOICEPASS,//选择题已通过
	   CHOICEEXPIRED,//选择题考试超时
	   OPENBEGIN,//开放题已开始
	   OPENCHOOSE,//已选择开放题，考试进行中
	   OPENSUBMIT,//开放题考试已提交
	   OPENREVIEWED,//开放题已批阅，考试结束
	   OPENEXPIRED,//开放题考试超时
	   };
   public static final String[] nextStepArr=
	  {"付款",
	   "开始考试",
	   "继续考试",
	   "查看选择题成绩",
	   "进入开放题",
	   "无",
	   "选择开放题",
	   "提交开放题结果",
	   "等待成绩通知",
	   "等待成绩通知",
	   "查看选择题成绩"};
   public static final int CHOICENUMBER = 20;
}
