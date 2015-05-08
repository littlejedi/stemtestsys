package com.web;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcel {
	public int readExcel(String filePath) throws Exception {
		int amount = 0;
		InputStream iStream = null;
		Workbook workbook = null;
		iStream = new FileInputStream(filePath);
		workbook = Workbook.getWorkbook(iStream);
		// sheet row column 下标都是从0开始的
		Sheet sheet = workbook.getSheet(0);
		int column = sheet.getColumns();
		int rows = sheet.getRows();
		System.out.println("共有" + rows + "行，" + column + "列数据");
		for (int i = 1; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);
			if (cells.length == 4) {
				System.out.println(cells[0].getContents());
				System.out.println(cells[1].getContents());
				System.out.println(cells[2].getContents());
				System.out.println(cells[3].getContents());
			}
			amount++;
		}
		// 操作完成时，关闭对象，释放占用的内存空间
		if (iStream != null)
			iStream.close();
		if (workbook != null)
			workbook.close();

		return amount;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String filePath = "D:\\testreadexcel.xls";
		ReadExcel readExcel = new ReadExcel();
		readExcel.readExcel(filePath);

	}

	
}