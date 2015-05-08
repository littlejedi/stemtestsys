package com.testsys.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.testsys.admin.bean.Admin;
import com.testsys.admin.dao.BaseDao;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.ChoiceAllo;
import com.testsys.web.bean.ChoiceResult;
import com.testsys.web.bean.Exam;
import com.testsys.web.bean.Myexam;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.OpenAllo;
import com.testsys.web.bean.Option;
import com.testsys.web.bean.Paper;
import com.testsys.web.bean.Profile;
import com.testsys.web.bean.Progress;
import com.testsys.web.bean.Review;


@Repository
public class StemDao extends BaseDao {
	/*
	 * @Autowired private JdbcTemplate jdbcTemplate;
	 */
	// ��ȡ�����б�
	public List<Exam> getExamList(int userId, int begin, int num) {
		String sqlStr = " Select es_exam.exam_id,es_exam.exam_name,es_exam.exam_society,es_exam.exam_subject,es_exam.exam_begin,exam_end,user_id FROM es_exam "
				+ " LEFT JOIN es_exam_paper ON es_exam_paper.exam_id = es_exam.exam_id "
				+ " AND es_exam_paper.user_id =? where es_exam.delFlg = 0 order by es_exam.exam_id desc limit ?,?;";
		// @SuppressWarnings("unchecked")
		List<Exam> examList = jdbcTemplate.query(sqlStr, new Object[] { userId,
				begin, num }, new RowMapper<Exam>() {
			// @Autowired
			public Exam mapRow(ResultSet rs, int rowNumber) throws SQLException {
				Exam exam = new Exam();

				exam.setExamId(rs.getInt("exam_id"));
				exam.setExamName(rs.getString("exam_name"));
				exam.setExamSociety(rs.getString("exam_society"));
				exam.setExamSubject(rs.getString("exam_subject"));
				String date = rs.getString("exam_begin");
				date = date.substring(0, date.length() - 2);
				exam.setExamBegin(date);// rs.getString("exam_begin"));
				// System.out.println(date);
				date = rs.getString("exam_end");
				date = date.substring(0, date.length() - 2);
				exam.setExamEnd(date);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String nowstr = sdf.format(now);
				int result = nowstr.compareTo(exam.getExamEnd());
				if (result > 0)
					exam.setStatus(1);
				else if (rs.getInt("user_id") != 0)
					exam.setStatus(2);
				else
					exam.setStatus(0);
				return exam;
			}
		});
		return examList;
	}

	// ��ȡ������Ϣ
	public Profile getProfileInfo(int userId) {

		String sqlStr = "select *  from es_user where user_id = ? ";
		final Profile info = new Profile();
		jdbcTemplate.query(sqlStr, new Object[] { userId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						if (rs.getString("idcard") != null)
							info.setIdcard(rs.getString("idcard"));
						else
							info.setIdcard("");
						if (rs.getString("telephonenumber") != null)
							info.setTelephonenumber(rs
									.getString("telephonenumber"));
						else
							info.setTelephonenumber("");
						
						if (rs.getString("realname") != null)
							info.setRealname(rs.getString("realname"));
						else
							info.setRealname("");
						info.setUserId(rs.getInt("user_id"));
						info.setName(rs.getString("name"));
						
						info.setExamTimes(rs.getInt("exam_times"));

					}
				});
		return info;
	}

	// ��ȡ�Ƿ��б�����Ϣ
	public int getMatchExamPaperCount(int userId, int examId) {
		return this.getMatchCount("es_exam_paper", " user_id = " + userId
				+ " and exam_id = " + examId + " ");
	}

	public int getPaperId(int userId,int examId)
	{
		String sqlStr = "select paper_id from es_exam_paper where user_id = "+userId+" and exam_id = "+examId;
		int paperId = jdbcTemplate.queryForInt(sqlStr);
		System.out.println("paperId"+paperId);
		return paperId;
	}
	// ����������Ϣ
	public int registerExam(int userId, int examId) {
		return this.insertAction("es_exam_paper", "user_id,exam_id", userId
				+ "," + examId);
	}

	public ChoiceResult getChoiceResult(int resultId) {

		String sqlStr = "select *  from es_exam_result where result_id = ? ";
		final ChoiceResult cr = new ChoiceResult();
		jdbcTemplate.query(sqlStr, new Object[] { resultId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						cr.setChoices(rs.getDouble("choice_s"));
						cr.setChoicet(rs.getDouble("choice_t"));
						cr.setChoicee(rs.getDouble("choice_e"));
						cr.setChoicem(rs.getDouble("choice_m"));
						cr.setChoicescore(rs.getInt("choice_score"));
						cr.setStatus(rs.getInt("status"));
						String date = rs.getString("create_time");
						date = date.substring(0, date.length() - 2);
						cr.setCreatetime(date);
					}
				});
		return cr;
	}

	public List<Admin> getAdminList(int begin, int num, int role) {
		String sqlStr = " Select * from es_admin where role = ? and delFlg = 0 order by admin_id desc limit ?,?;";
		// @SuppressWarnings("unchecked")
		List<Admin> adminList = jdbcTemplate.query(sqlStr, new Object[] { role,
				begin, num }, new RowMapper<Admin>() {
			public Admin mapRow(ResultSet rs, int rowNumber)
					throws SQLException {
				Admin admin = new Admin();
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setAccount(rs.getString("account"));
				admin.setRole(rs.getInt("role"));
				admin.setPassword(rs.getString("password"));
				if (rs.getString("course") != null)
					admin.setCourse(rs.getString("course"));
				admin.setName(rs.getString("name"));
				String date = rs.getString("create_time");
				date = date.substring(0, date.length() - 2);
				admin.setCreateTime(date);
				return admin;
			}
		});
		return adminList;
	}

	public ChoiceAllo findChoiceAllo(int choiceAlloId) {

		String sqlStr = "select * " + " from es_choice_allocation as ca "
				+ " where ca.choice_allo_id = ? ";
		final ChoiceAllo ca = new ChoiceAllo();
		jdbcTemplate.query(sqlStr, new Object[] { choiceAlloId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ca.setChoiceAlloId(rs.getInt("choice_allo_id"));
						ca.setChoiceIdList(rs.getString("choice_id_list"));
						ca.setUserAnswerList(rs.getString("user_answer_list"));
						String date = rs.getString("create_time");
						date = date.substring(0, date.length() - 2);
						ca.setCreateTime(date);
					}
				});
		return ca;
	}

	public List<Option> findOptionListByChoiceId(int choiceId) {
		// List<Admin> adminlist=new ArrayList<Admin>();
		String sqlStr = " Select * from es_choice_option where choice_id = ?";
		// @SuppressWarnings("unchecked")
		List<Option> optionList = jdbcTemplate.query(sqlStr,
				new Object[] { choiceId }, new RowMapper<Option>() {
					public Option mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						Option op = new Option();

						op.setChoiceId(rs.getInt("choice_id"));
						op.setOptionContent(rs.getString("option_content"));
						op.setOptionId(rs.getInt("option_id"));
						op.setOptionImg(rs.getString("option_img"));
						op.setOptionWeight(rs.getInt("option_weight"));
						op.setIdentifier(rs.getString("option_identifier"));
						return op;
					}
				});
		return optionList;
	}

	public List<Choice> findAllChoiceByChoiceId(String choiceIdList) {
		String sqlStr = " Select * from es_choice_question where choice_id in ("
				+ choiceIdList + ") ";
		// @SuppressWarnings("unchecked")
		List<Choice> choiceList = jdbcTemplate.query(sqlStr,
				new RowMapper<Choice>() {
					public Choice mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						Choice c = new Choice();

						c.setChoiceId(rs.getInt("choice_id"));
						c.setCourse(rs.getString("course"));
						c.setType(rs.getString("type"));

						String date = rs.getString("create_time");
						date = date.substring(0, date.length() - 2);
						c.setCreateTime(date);

						c.setTitle(rs.getString("title"));
						c.setImg(rs.getString("img"));

						c.setS(rs.getInt("S"));
						c.setT(rs.getInt("T"));
						c.setE(rs.getInt("E"));
						c.setM(rs.getInt("M"));
						return c;
					}
				});
		return choiceList;
	}

	public Option findOptionInfoByOptionId(int optionId) {
		String sqlStr = " Select * from es_choice_option where option_id = ?";
		final Option op = new Option();
		jdbcTemplate.query(sqlStr, new Object[] { optionId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {

						op.setChoiceId(rs.getInt("choice_id"));
						op.setOptionContent(rs.getString("option_content"));
						op.setOptionId(rs.getInt("option_id"));
						op.setOptionImg(rs.getString("option_img"));
						op.setOptionWeight(rs.getInt("option_weight"));
						op.setIdentifier(rs.getString("option_identifier"));
					}
				});
		return op;
	}

	public Choice findChoiceInfoById(int choiceId) {

		String sqlStr = "select * " + "from es_choice_question as ec "
				+ "where ec.choice_id = ?";
		final Choice c = new Choice();
		jdbcTemplate.query(sqlStr, new Object[] { choiceId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {

						c.setChoiceId(rs.getInt("choice_id"));
						c.setCourse(rs.getString("course"));
						c.setType(rs.getString("type"));

						String date = rs.getString("create_time");
						date = date.substring(0, date.length() - 2);
						c.setCreateTime(date);

						c.setTitle(rs.getString("title"));
						c.setImg(rs.getString("img"));

						c.setS(rs.getInt("S"));
						c.setT(rs.getInt("T"));
						c.setE(rs.getInt("E"));
						c.setM(rs.getInt("M"));
					}
				});
		return c;
	}

	public Progress findProgressInfo(int choiceAlloId) {

		String sqlStr = "select * " + "from es_choice_progress as cp "
				+ "where cp.choice_allo_id = ?";
		final Progress p = new Progress();
		jdbcTemplate.query(sqlStr, new Object[] { choiceAlloId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						p.setProgressId(rs.getInt("progress_id"));
						p.setBeginTime(rs.getInt("begin_time"));
						p.setChoiceAlloId(rs.getInt("choice_allo_id"));
						p.setCurChoiceId(rs.getInt("cur_choice_id"));
					}
				});
		return p;
	}

	public int generateProgress(int choiceAlloId, int nextChoiceId) {
		String sqlStr = "insert into es_choice_progress (choice_allo_id,cur_choice_id) values (?,?)";
		int[] types = new int[] { Types.INTEGER, Types.INTEGER };
		int insertId = jdbcTemplate.update(sqlStr, new Object[] { choiceAlloId,
				nextChoiceId }, types);
		return insertId;
	}

	public void updateProgress(int progressId, int nextChoiceId) {
		String sqlStr = "update es_choice_progress set cur_choice_id = ? where progress_id = ?";
		int[] types = new int[] { Types.INTEGER, Types.INTEGER };
		jdbcTemplate.update(sqlStr, new Object[] { nextChoiceId, progressId },
				types);
	}

	public void updateChoiceResult(int choiceAlloId, String userAnswerList) {
		String sqlStr = "update es_choice_allocation set user_answer_list = ? where choice_allo_id = ?";
		int[] types = new int[] { Types.VARCHAR, Types.INTEGER };
		jdbcTemplate.update(sqlStr,
				new Object[] { userAnswerList, choiceAlloId }, types);
	}

	public Admin findUserByAdminId(final int adminId) {
		String sqlStr = " SELECT * "
				+ " FROM es_admin WHERE admin_id =? and delFlg = 0";
		final Admin admin = new Admin();
		jdbcTemplate.query(sqlStr, new Object[] { adminId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						admin.setAdminId(rs.getInt("admin_id"));
						admin.setAccount(rs.getString("account"));
						admin.setRole(rs.getInt("role"));
						admin.setPassword(rs.getString("password"));
						admin.setName(rs.getString("name"));
					}
				});
		return admin;
	}

	public int getOpenNumbers(int adminId) {
		String sqlStr = " SELECT count(*) FROM es_open_question "
				+ " WHERE admin_id=? ";
		return jdbcTemplate.queryForInt(sqlStr, new Object[] { adminId });
	}

	public boolean getAlloMatch(int choiceAlloId, int userId) {
		String sqlStr = "select count(*) "
				+ "from es_choice_allocation as ca,es_exam_paper as ep "
				+ "where ca.choice_allo_id = ep.choice_allo_id and ep.choice_allo_id = ? and ep.user_id = ?";
		int count = jdbcTemplate.queryForInt(sqlStr, new Object[] {
				choiceAlloId, userId });
		if (count > 0)
			return true;
		else
			return false;
	}

	public List<Myexam> getMyExamList(int userId, int begin, int num) {
		String sqlStr = "SELECT paper_id,exam_begin,exam_end, es_exam.exam_id,es_exam.exam_society,es_exam.exam_subject, status ";
		sqlStr += "FROM es_exam, es_exam_paper ";
		sqlStr += "WHERE es_exam_paper.user_id =? and es_exam.exam_id = es_exam_paper.exam_id ";
		sqlStr += "order by paper_id desc ";
		sqlStr += "LIMIT ?,?;";
		// @SuppressWarnings("unchecked")
		List<Myexam> myExamList = jdbcTemplate.query(sqlStr, new Object[] {
				userId, begin, num }, new RowMapper<Myexam>() {
			// @Autowired
			public Myexam mapRow(ResultSet rs, int rowNumber)
					throws SQLException {
				Myexam myexam = new Myexam();
				myexam.setExamId(rs.getInt("exam_id"));
                myexam.setExamSociety(rs.getString("exam_society"));
                myexam.setExamSubject(rs.getString("exam_subject"));
				myexam.setPaperId(rs.getInt("paper_id"));

				myexam.setStatus(rs.getInt("status"));

				String date = rs.getString("exam_begin");
				date = date.substring(0, date.length() - 2);
				myexam.setExamBegin(date);

				date = rs.getString("exam_end");
				date = date.substring(0, date.length() - 2);
				myexam.setExamEnd(date);

				return myexam;
			}
		});
		return myExamList;
	}

	public int getPaperStatus(int paperId) {
		// TODO Auto-generated method stub
		String sqlStr = " SELECT status "
				+ " FROM es_exam_paper WHERE paper_id =?";
		final Myexam exam = new Myexam();
		jdbcTemplate.query(sqlStr, new Object[] { paperId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						exam.setStatus(rs.getInt("status"));
					}
				});
		return exam.getStatus();
	}

	public List<Integer> getRandomChoiceList(int choiceNumber) {
		String sqlStr = "SELECT choice_id ";
		sqlStr += "FROM es_choice_question ";
		sqlStr += " where delFlg = 0 and is_verify = 1 ";
		sqlStr += "order by rand() ";
		sqlStr += "LIMIT " + choiceNumber;
		System.out.println(sqlStr);
		// @SuppressWarnings("unchecked")
		List<Integer> choicelist = jdbcTemplate.query(sqlStr,
				new RowMapper<Integer>() {
					// @Autowired
					public Integer mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						int choice_id = rs.getInt("choice_id");
						return choice_id;
					}
				});
		return choicelist;
	}

	public Paper getPaperInfo(int paperId) {
		String sqlStr = "select * from es_exam_paper where paper_id = ?";
		final Paper paper = new Paper();
		jdbcTemplate.query(sqlStr, new Object[] { paperId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						paper.setChoiceAlloId(rs.getInt("choice_allo_id"));
						paper.setExamId(rs.getInt("exam_id"));
						paper.setOpenAlloId(rs.getInt("open_allo_id"));
						paper.setPaperId(rs.getInt("paper_id"));
						paper.setPayId(rs.getInt("pay_id"));
						paper.setResultId(rs.getInt("result_id"));
						paper.setStatus(rs.getInt("status"));
						paper.setUserId(rs.getInt("user_id"));

					}
				});
		return paper;
	}

	public List<Integer> getRandomOpenList(String course) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT open_id ";
		sqlStr += "FROM es_open_question ";
		sqlStr += " where delFlg = 0 and is_verify = 1 and course = '" + course
				+ "'";
		sqlStr += "order by rand() ";
		sqlStr += "LIMIT 3";// + choiceNumber;
		// @SuppressWarnings("unchecked")
		List<Integer> openlist = jdbcTemplate.query(sqlStr,
				new RowMapper<Integer>() {
					// @Autowired
					public Integer mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						int open_id = rs.getInt("open_id");
						return open_id;
					}
				});
		return openlist;
	}

	public OpenAllo findOpenAllo(int openAlloId) {

		String sqlStr = "select * " + " from es_open_allocation as oa "
				+ " where oa.open_allo_id = ? ";
		final OpenAllo oa = new OpenAllo();
		jdbcTemplate.query(sqlStr, new Object[] { openAlloId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						oa.setOpenAlloId(rs.getInt("open_allo_id"));
						oa.setCourse(rs.getString("course"));
						oa.setOptionalOpenId(rs.getString("optional_open_id"));
						oa.setSelectedOpenId(rs.getString("selected_open_id"));
						oa.setAnswerFileSrc(rs.getString("answer_file_src"));
						String date = rs.getString("begin_time");
						date = date.substring(0, date.length() - 2);
						oa.setBeginTime(date);
					}
				});
		return oa;
	}

	public Open getOpenQuestionById(Integer openId) {
		// TODO Auto-generated method stub
		String sqlStr = "select * " + " from es_open_question as oq "
				+ " where oq.open_id = ? ";
		final Open open = new Open();
		jdbcTemplate.query(sqlStr, new Object[] { openId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						open.setTitle(rs.getString("title"));
						open.setOpenId(rs.getInt("open_id"));
						open.setImg(rs.getString("img"));
						open.setAs_example(rs.getString("as_example"));
						open.setDescription(rs.getString("description"));
						open.setTest_point(rs.getString("test_point"));
					}
				});
		return open;
	}

	public List<Integer> getRandomAdminList(String course) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT admin_id ";
		sqlStr += "FROM es_admin ";
		sqlStr += " where delFlg = 0 and role = 3 and course = '" + course
				+ "'";
		sqlStr += "order by rand() ";
		sqlStr += "LIMIT 2";// + choiceNumber;
		// @SuppressWarnings("unchecked")
		List<Integer> openlist = jdbcTemplate.query(sqlStr,
				new RowMapper<Integer>() {
					// @Autowired
					public Integer mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						int open_id = rs.getInt("admin_id");
						return open_id;
					}
				});
		return openlist;
	}

	public List<Review> getReviewList(int openAlloId) {
		// TODO Auto-generated method stub
		String sqlStr = "select * " + " from es_open_review as oq "
				+ " where oq.open_allo_id =  " + openAlloId;
		// @SuppressWarnings("unchecked")
		List<Review> reviewlist = jdbcTemplate.query(sqlStr,
				new RowMapper<Review>() {
					// @Autowired
					public Review mapRow(ResultSet rs, int rowNumber)
							throws SQLException {
						Review review = new Review();
						String date = rs.getString("begin_time");
						if (date != null)
							date = date.substring(0, date.length() - 2);
						else
							date = "";
						review.setBeginTime(date);
						review.setOpenAlloId(rs.getInt("open_allo_id"));
						review.setReviewId(rs.getInt("review_id"));
						review.setAdminId(rs.getInt("admin_id"));
						review.setS(rs.getInt("s"));
						review.setT(rs.getInt("t"));
						review.setE(rs.getInt("e"));
						review.setM(rs.getInt("m"));
						review.setReviewResult(rs.getInt("review_result"));
						return review;
					}
				});
		return reviewlist;
	}

	public Review getReviewById(Integer reviewId) {
		// TODO Auto-generated method stub
		String sqlStr = "select * " + " from es_open_review as oq "
				+ " where oq.review_id = ? ";
		final Review review = new Review();
		jdbcTemplate.query(sqlStr, new Object[] { reviewId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						String date = rs.getString("begin_time");
						if (date != null)
							date = date.substring(0, date.length() - 2);
						else
							date = "";
						review.setBeginTime(date);
						review.setOpenAlloId(rs.getInt("open_allo_id"));
						review.setReviewId(rs.getInt("review_id"));
						review.setAdminId(rs.getInt("admin_id"));
						review.setStatus(rs.getInt("status"));
					}
				});
		return review;
	}

	public Paper getPaperInfoByOpenAlloId(int openAlloId) {
		String sqlStr = "select * from es_exam_paper where open_allo_id = ?";
		final Paper paper = new Paper();
		jdbcTemplate.query(sqlStr, new Object[] { openAlloId },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						paper.setChoiceAlloId(rs.getInt("choice_allo_id"));
						paper.setExamId(rs.getInt("exam_id"));
						paper.setOpenAlloId(rs.getInt("open_allo_id"));
						paper.setPaperId(rs.getInt("paper_id"));
						paper.setPayId(rs.getInt("pay_id"));
						paper.setResultId(rs.getInt("result_id"));
						paper.setStatus(rs.getInt("status"));
						paper.setUserId(rs.getInt("user_id"));

					}
				});
		return paper;
	}

	public int getNearestExam(String nowstr) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT exam_id FROM `es_exam` WHERE exam_begin >'"+nowstr+"' order by exam_begin limit 1";
		List list = jdbcTemplate.queryForList(sqlStr);
		int examId = 0;
		if(list.size() != 0)
		{
			Map p = (Map) list.get(0);
			examId = (Integer) p.get("exam_id");
		}
		//int examId = jdbcTemplate.queryForInt(sqlStr);
		System.out.println(examId);
		//List rows = jdbcTemplate.queryForList("SELECT * FROM USER");  
		
		return examId;
	}

	public List<ChoiceResult> getChoiceResultList(String idcard) {
		String sqlStr = " SELECT choice_score,choice_s,choice_t,choice_e,choice_m FROM es_exam_paper as ep left join es_exam_result as er on ep.result_id=er.result_id left join es_user as u on  ep.user_id=u.user_id where u.idcard='"+idcard+"';";
		// @SuppressWarnings("unchecked")
		List<ChoiceResult> resultList = jdbcTemplate.query(sqlStr,  new RowMapper<ChoiceResult>() {
			public ChoiceResult mapRow(ResultSet rs, int rowNumber)
					throws SQLException {
				ChoiceResult cr = new ChoiceResult();
				//if(rs.getDouble("choice_s") == null)
				cr.setChoices(rs.getDouble("choice_s"));
				cr.setChoicet(rs.getDouble("choice_t"));
				cr.setChoicee(rs.getDouble("choice_e"));
				cr.setChoicem(rs.getDouble("choice_m"));
				cr.setChoicescore(rs.getInt("choice_score"));
				return cr;
				
			}
		});
		return resultList;
	}
	//根据考试名称得到paperinfo，之后继续使用paperId
	//SELECT choice_score,choice_s,choice_t,choice_e,choice_m FROM es_exam_paper as ep left join es_exam_result as er on ep.result_id=er.result_id left join es_user as u on  ep.user_id=u.user_id where u.idcard='112' 
	public Paper getPaperInfobyexamname(String examName) {
		String sqlStr = "select * from es_exam_paper,es_exam where es_exam.exam_name = ?";
		       sqlStr +=" and es_exam.exam_id=es_exam_paper.exam_id";
		final Paper paper = new Paper();
		jdbcTemplate.query(sqlStr, new Object[] { examName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						paper.setChoiceAlloId(rs.getInt("choice_allo_id"));
						paper.setExamId(rs.getInt("exam_id"));
						paper.setOpenAlloId(rs.getInt("open_allo_id"));
						paper.setPaperId(rs.getInt("paper_id"));
						paper.setPayId(rs.getInt("pay_id"));
						paper.setResultId(rs.getInt("result_id"));
						paper.setStatus(rs.getInt("status"));
						paper.setUserId(rs.getInt("user_id"));

					}
				});
		return paper;
	}
}
