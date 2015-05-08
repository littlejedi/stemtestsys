package com.testsys.admin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testsys.web.bean.Choice;
import com.testsys.web.bean.Open;
import com.testsys.web.bean.Option;
import com.testsys.admin.bean.Page;
import com.testsys.admin.bean.PageHelper;
import com.testsys.admin.dao.AdminChoiceDao;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
@Service
public class EcAdminService {
	@Autowired
	private AdminChoiceDao choicedao;

	private int adminId;
	private String folder;
	private String[] openFields = { "title", "img", "as_example", "test_point",
			"description", "course", "difficulty", "examiner", "is_verify",
			"verifier", "modified_recommend" };
	private String[] optionFields = { "option_content", "option_img",
			"option_weight" };
	private String[] choiceFields = { "title", "img", "S", "T", "E", "M",
			"course", "difficulty", "type", "examiner", "is_verify",
			"verifier", "modified_recommend" };

	public void readUploadedExcel(File file, String imagepath, int adminId)
			throws IOException {
		this.adminId = adminId;
		this.folder = imagepath;
		System.out.print("folder"+imagepath);
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		int sheetNumber = xwb.getNumberOfSheets();
		for (int i = 0; i < sheetNumber; i++) {
			XSSFSheet sheet = xwb.getSheetAt(i);
			if (i == 0) {
				// common choice question
				this.readCommonChoiceFromExcel(sheet);
			} else if (i == 1) {
				// interlink choice question
				this.readInterlinkChoiceFromExcel(sheet);
			} else if (i == 2) {
				// open question
				this.readOpenQuestionFromExcel(sheet);
			}
			if (i > 2)
				break;
		}
	}

	public void readCommonChoiceFromExcel(XSSFSheet sheet) throws IOException 
	{
		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		String masterCode = "";
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			String fields = "";
			String values = "";
			boolean isChoice = false;
			String[][] options = new String[4][3];
			boolean isVerify = true;
			boolean is_first = true;
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {

				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				value = this.readObjectFromCell(cell);
				if (j == 0 && !String.valueOf(value).equals("") && i >= 5)
					isChoice = true;
				if (value == null || "".equals(value)) {
					continue;
				}
				// 读取所有设置的字段
				int index = -1;
				if (j == 1 || j == 2) {
					index = j - 1;
				}
				// 读取选项字段
				else if (j >= 3 && j <= 10) {
					options[(j - 3) / 2][(j - 1) % 2] = String.valueOf(value);
				}
				// 读取选项字段
				else if (j >= 11 && j <= 14) {
					options[j - 11][2] = String.valueOf(value);
				} else if (j >= 15 && j <= 18) {
					index = j - 13;
				} else if (j == 21) {
					index = j - 15;
				} else if (j >= 24 && j <= 29) {
					index = j - 17;
				}

				// 生成fields和value的sql语句
				if (index != -1 && i >= 5) {
					String data = String.valueOf(value);
					if (j == 2 && !data.equals(""))
					{
						data = nobeginempty(data);
						if(!data.equals(""))
							data = folder + "/" + masterCode + "/" + data;
					}
					if (j == 21)
						data = this.setCourse(data);
					if (j == 24)
						data = this.setDifficulty(data);
					if (j == 25)
						data = this.setType(data);
					if (j == 27) {
						data = this.setVerify(data);
						if (!data.equals("1"))
							isVerify = false;
					}
					if (is_first){//j == 1) {
						fields = choiceFields[index];
						values = "'" + data + "'";
						is_first = false;
					} else {
						fields += "," + choiceFields[index];
						
						values += ",'" + data + "'";
					}
				}
				// 读取学会名称
				if (i == 1 && j == 1)
					masterCode = (String) value;
			}
			if (isChoice && isVerify) {
				fields += ",admin_id";
				values += ",'" + this.adminId + "'";
				int choiceId = choicedao.addChoice(fields, values);
				char identifier = 'A';
				for (int a = 0; a < 4; a++) {
					String optionfields = "choice_id";
					String optionvalues = choiceId + "";
					for (int b = 0; b < 3; b++) {
						if (options[a][b] != null && !options[a][b].equals("")) {
							optionfields += "," + optionFields[b];
							if(b == 1)
							{
								options[a][b] = nobeginempty(options[a][b]);
								if(!options[a][b].equals(""))
									optionvalues += ",'"+ folder + "/" + masterCode
											+ "/" + options[a][b] + "'";
								else
									optionvalues += ",'"+ options[a][b] + "'";
							}
							else
							{
								optionvalues += ",'"+ options[a][b] + "'";
							}
							//optionvalues += ",'"
							//		+ ((b == 1) ? (folder + "/" + masterCode
							//				+ "/" + options[a][b])
							//				: options[a][b]) + "'";
						}
					}
					optionfields += ",option_identifier";
					optionvalues += ",'" + identifier + "'";
					choicedao.addOption(optionfields, optionvalues);
					identifier++;
				}

			}
		}
	}
	public String nobeginempty(String str)
	{
		while(str.length()!=0)
		{
			if(str.substring(0, 1).equals(" "))
				str = str.substring(1);
			else
				break;
		}
		return str;
	}
	public void readInterlinkChoiceFromExcel(XSSFSheet sheet)
			throws IOException {
		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		String masterCode = "";

		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			String fields = "";
			String values = "";
			boolean isChoice = false;
			String[][] options = new String[8][3];
			boolean isVerify = true;
			boolean is_first = true;
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {

				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				value = this.readObjectFromCell(cell);
				if (j == 0 && !String.valueOf(value).equals("") && i >= 5)
					isChoice = true;
				if (value == null || "".equals(value)) {
					continue;
				}
				// 读取所有设置的字段
				int index = -1;
				if (j == 1 || j == 2) {
					index = j - 1;
				}
				// 读取选项字段
				else if (j >= 3 && j <= 18) {
					options[(j - 3) / 2][(j - 1) % 2] = String.valueOf(value);
				}
				// 读取选项字段
				else if (j >= 19 && j <= 26) {
					options[j - 19][2] = String.valueOf(value);
				} else if (j >= 27 && j <= 30)// +12
				{
					index = j - 25;
				} else if (j == 33) {
					index = j - 27;
				} else if (j >= 36 && j <= 41) {
					index = j - 29;
				}

				// 生成fields和value的sql语句
				if (index != -1 && i >= 5) {
					String data = String.valueOf(value);
					if (j == 2 && !data.equals(""))
					{
						data = nobeginempty(data);
						if(!data.equals(""))//add
							data = folder + "/" + masterCode + "/" + data;
					}
					if (j == 33)
						data = this.setCourse(data);
					if (j == 36)
						data = this.setDifficulty(data);
					if (j == 37)
						data = this.setType(data);
					if (j == 39) {
						data = this.setVerify(data);
						if (!data.equals("1"))
							isVerify = false;
					}
					if (is_first){//j == 1) {
						fields = choiceFields[index];
						values = "'" + data + "'";
						is_first = false;
					} else {
						fields += "," + choiceFields[index];
						
						values += ",'" + data + "'";

					}
				}
				// 读取学会名称
				if (i == 1 && j == 1)
					masterCode = (String) value;
			}
			if (isChoice && isVerify) {
				fields += ",admin_id";
				values += ",'" + this.adminId + "'";
				int choiceId = choicedao.addChoice(fields, values);
				char identifier = 'A';
				for (int a = 0; a < 8; a++) {
					String optionfields = "choice_id";
					String optionvalues = choiceId + "";
					for (int b = 0; b < 3; b++) {
						if (options[a][b] != null && !options[a][b].equals("")) {
							optionfields += "," + optionFields[b];
							if(b == 1)
							{
								options[a][b] = nobeginempty(options[a][b]);
								if(!options[a][b].equals(""))
									optionvalues += ",'"+ folder + "/" + masterCode
											+ "/" + options[a][b] + "'";
								else
									optionvalues += ",'"+ options[a][b] + "'";
							}
							else
							{
								optionvalues += ",'"+ options[a][b] + "'";
							}
							//optionvalues += ",'"+ ((b == 1) ? (folder + "/" + masterCode
							//				+ "/" + options[a][b])
							//				: options[a][b]) + "'";
						}
					}
					optionfields += ",option_identifier";
					optionvalues += ",'" + identifier + "'";
					choicedao.addOption(optionfields, optionvalues);
					identifier++;
				}

			}
		}
	}

	public List<Page> getPageList(String path, int page, int num, int count) {
		PageHelper p = new PageHelper();
		p.setTotal(count);
		p.setPageSize(num);
		p.setPath(path);
		p.setIndex(page);
		return p.getPageList();
	}

	public String readObjectFromCell(XSSFCell cell) {
		Object value = null;
		String result = "";
		DecimalFormat df = new DecimalFormat("0");//
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			value = df.format(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			try {
				value = cell.getStringCellValue();
			} catch (IllegalStateException e) {
				value = cell.getNumericCellValue();
			}
			break;
		default:
			value = cell.toString();
		}
		result = String.valueOf(value);
		return result;
	}

	public void readOpenQuestionFromExcel(XSSFSheet sheet) throws IOException {
		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		String masterCode = "";
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			String fields = "";
			String values = "";
			boolean isOpen = false;
			boolean isVerify = true;
			boolean is_first = true;
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {

				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				value = this.readObjectFromCell(cell);
				if (j == 1 && !String.valueOf(value).equals("") && i >= 5)
					isOpen = true;
				if (value == null || "".equals(value)) {
					continue;
				}
				// 读取所有设置的字段
				int index = -1;
				if (j >= 1 && j <= 11) {
					index = j - 1;
				}

				// 生成fields和value的sql语句
				if (index != -1 && i >= 5) {
					String data = String.valueOf(value);
					if (j == 2 && !data.equals(""))
					{
						data = nobeginempty(data);
						if(!data.equals(""))//add
						data = folder + "/" + masterCode + "/" + data;
					}
					else if (j == 6)
						data = this.setCourse(data);
					else if (j == 7)
						data = this.setDifficulty(data);
					else if (j == 9) {
						data = this.setVerify(data);
						if (!data.equals("1"))
							isVerify = false;
					}
					if (is_first){//j == 1) {
						fields = openFields[index];
						values = "'" + data + "'";
						is_first= false;
					} else {
						fields += "," + openFields[index];
						
						values += ",'" + data + "'";
					}
				}
				// 读取学会名称
				if (i == 1 && j == 1)
					masterCode = (String) value;
			}
			if (isOpen && isVerify) {
				fields += ",admin_id";
				values += ",'" + this.adminId + "'";
				/*int openId = */choicedao.insertAction("es_open_question", fields,values);
			}
		}
	}

	public String setCourse(String course) {
		if (course.equals("生命科学")) {
			return "L";
		} else if (course.equals("物质科学")) {
			return "M";
		} else if (course.equals("技术与设计")) {
			return "T";
		} else if (course.equals("地球与环境科学")) {
			return "E";
		} else if (course.equals("社会及行为科学")) {
			return "S";
		}
		return "N";
	}

	public String setDifficulty(String difficulty) {
		if (difficulty.equals("高"))
			return "2";
		else if (difficulty.equals("中"))
			return "1";
		else if (difficulty.equals("低"))
			return "0";
		return "3";
	}

	public String setType(String type) {
		if (type.equals("知识点"))
			return "K";
		else if (type.equals("方法论"))
			return "F";
		return "N";
	}

	public String setVerify(String verify) {
		if (verify.equals("是"))
			return "1";
		else if (verify.equals("否"))
			return "2";
		return "0";
	}

	public List<Choice> getChoiceList(int page, int pagenum, int adminId,
			String condition) {
		// TODO Auto-generated method stub
		List<Choice> choicelist = choicedao.getChoiceList(page * pagenum
				- pagenum, pagenum, adminId, condition);
		return choicelist;
	}

	public List<Open> getOpenList(int page, int pagenum, int adminId,
			String condition) {
		// TODO Auto-generated method stub
		List<Open> openlist = choicedao.getOpenList(page * pagenum - pagenum,
				pagenum, adminId, condition);
		return openlist;
	}

	public int getMatchChoiceCount(int adminId, String condition) {
		return choicedao.getMatchChoiceCount(adminId, condition);
	}

	public Choice getChoiceById(int choiceId) {
		return choicedao.findChoiceInfoById(choiceId);
	}

	public List<Option> getOptionListById(int choiceId) {
		return choicedao.findOptionListByChoiceId(choiceId);
	}

	public String getChoiceInfo(int choiceId) 
	{
		Choice choice = choicedao.findChoiceInfoById(choiceId);
		List<Option> options = choicedao.findOptionListByChoiceId(choiceId);
		JSONObject json = new JSONObject();
		JSONArray optionList = new JSONArray();
		Iterator<Option> itr = options.listIterator();
		while (itr.hasNext()) {
			Option option = itr.next();
			JSONObject optionObj = new JSONObject();
			optionObj.put("identifier", option.getIdentifier());
			optionObj.put("content", option.getOptionContent());
			optionObj.put("optionId", option.getOptionId());
			// 需要拼接生成可直接访问的地址
			optionObj.put("img", option.getOptionImg());
			optionObj.put("point", option.getOptionWeight());
			optionList.add(optionObj);
		}
		json.put("choiceId", choice.getChoiceId());
		json.put("title", choice.getTitle());
		json.put("img", choice.getImg());
		json.put("options", optionList);

		return json.toString();
	}

	// 删除选择题
	public boolean removeChoice(String choiceIdList) {
		String[] choiceIdArr = choiceIdList.split(",");
		// 如果此题目已被选择进入考试，能否删除
		for (int i = 0; i < choiceIdArr.length; i++) {
			int choiceId = Integer.valueOf(choiceIdArr[i]);
			choicedao.removeChoice(choiceId);
		}
		return true;
	}

	public void updateOption(String optionId, String content, String point) {
		// TODO Auto-generated method stub
		choicedao.updateAction("es_choice_option", " option_content='"
				+ content + "',option_weight='" + point + "' ", " option_id="
				+ optionId + " ");
	}

	public void updateChoiceImg(String choiceId, String img) {
		choicedao.updateAction("es_choice_question", "img = '" + img + "'",
				"choice_id=" + choiceId);
	}

	public void updateOpenImg(String openId, String img) {
		choicedao.updateAction("es_open_question", "img = '" + img + "'",
				"open_id=" + openId);
	}

	public void updateOptionImg(String optionId, String img) {
		choicedao.updateAction("es_choice_option",
				"option_img = '" + img + "'", "option_id=" + optionId);
	}

	public int getMatchOpenCount(int adminId, String condition) {
		// TODO Auto-generated method stub
		return choicedao.getMatchOpenCount(adminId, condition);
	}

	public boolean removeOpen(String openIdList) {
		// TODO Auto-generated method stub
		String[] choiceIdArr = openIdList.split(",");
		// 如果此题目已被选择进入考试，能否删除
		for (int i = 0; i < choiceIdArr.length; i++) {
			int choiceId = Integer.valueOf(choiceIdArr[i]);
			choicedao.removeOpen(choiceId);
		}
		return true;
	}

	public Open getOpenInfoById(int openId) {
		// TODO Auto-generated method stub
		return choicedao.getOpenInfoById(openId);
	}
}