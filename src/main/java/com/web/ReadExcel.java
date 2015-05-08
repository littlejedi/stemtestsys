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
		// sheet row column �±궼�Ǵ�0��ʼ��
		Sheet sheet = workbook.getSheet(0);
		int column = sheet.getColumns();
		int rows = sheet.getRows();
		System.out.println("����" + rows + "�У�" + column + "������");
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
		// �������ʱ���رն����ͷ�ռ�õ��ڴ�ռ�
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