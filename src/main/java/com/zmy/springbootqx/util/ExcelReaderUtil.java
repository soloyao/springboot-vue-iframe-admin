package com.zmy.springbootqx.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.zmy.springbootqx.pojo.Hero;

/**
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: ExcelReaderUtil.java
 * @Description: 读取Excel内容
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年12月25日 上午10:10:14
 */
public class ExcelReaderUtil {

	private static Logger logger = Logger.getLogger(ExcelReaderUtil.class.getName());
	
	private static final String XLS = "xls";
	private static final String XLSX = "xlsx";
	
	/**
	 * 根据文件后缀名类型获取对应的工作簿对象
	 * @param inputStream
	 * @param fileType
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
		Workbook workbook = null;
		if (fileType.equals(XLS)) {
			workbook = new HSSFWorkbook(inputStream);
		} else if (fileType.equals(XLSX)) {
			workbook = new XSSFWorkbook(inputStream);
		}
		return workbook;
	}
	
	/**
	 * 读取Excel文件内容
	 * @param file
	 * @return
	 */
	public static Object readExcel(MultipartFile file) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			String fileName = file.getOriginalFilename();
			//获取Excel后缀名
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (!fileType.equals(XLS) && !fileType.equals(XLSX)) {
				throw new Exception("不是xls或xlsx格式的文件，无法对其进行解析");
			}
			//获取Excel工作簿
			inputStream = (FileInputStream) file.getInputStream();
			workbook = getWorkbook(inputStream, fileType);
			
			//读取Excel中的数据
			List<Hero> heroList = parseExcel(workbook);
			
			return heroList;
		} catch (Exception e) {
			return "解析Excel失败， 文件名：" + file.getOriginalFilename() + " 错误信息：" + e.getMessage();
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (Exception e) {
				return "关闭数据流出错！错误信息：" + e.getMessage();
			}
		}
	}
	
	/**
	 * 解析Excel数据
	 * @param workbook
	 * @return
	 */
	private static List<Hero> parseExcel(Workbook workbook) {
		List<Hero> heroList = new ArrayList<Hero>();
		//解析Sheet
		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {//遍历Excel Sheet页签
			Sheet sheet = workbook.getSheetAt(sheetNum);
			
			//校验sheet是否合法
			if (sheet == null) {
				continue;
			}
			
			//获取第一行数据
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
			}
			
			//解析每一行的数据，构造数据对象
			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				
				if (null == row) {
					continue;
				}
				
				Hero hero = converRowToData(row);
				if (null == hero) {
					logger.warning("第" + row.getRowNum() + "行数据不合法，易忽略！");
					continue;
				}
				heroList.add(hero);
			}
		}
		
		return heroList;
	}
	
	/**
	 * 提取每一行中需要的数据，构造成为一个结果数据对象
	 * 
	 * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
	 * @param row
	 * @return
	 */
	private static Hero converRowToData(Row row) {
		Hero hero = new Hero();
		
		Cell cell;
		int cellNum = 0;
		//获取名字
		cell = row.getCell(cellNum++);
		String name = convertCellValueToString(cell);
		hero.setName(name);
		//获取血量
		cell = row.getCell(cellNum++);
		float hp = Float.parseFloat(convertCellValueToString(cell));
		hero.setHp(hp);
		
		return hero;
	}
	
	/**
	 * 将单元格内容转换为字符串
	 * @param cell
	 * @return
	 */
	private static String convertCellValueToString(Cell cell) {
		if (cell == null) {
			return null;
		}
		String returnValue = null;
		switch (cell.getCellType()) {
		case NUMERIC: //数字
			Double doubleValue = cell.getNumericCellValue();
			//格式化科学计数法，取一位整数
			DecimalFormat df = new DecimalFormat("0");
			returnValue = df.format(doubleValue);
			break;
		case STRING: //字符串
			returnValue = cell.getStringCellValue();
			break;
		case BOOLEAN: //布尔
			Boolean booleanValue = cell.getBooleanCellValue();
			returnValue = booleanValue.toString();
			break;
		case BLANK: //空值
			break;
		case FORMULA: //公式
			returnValue = cell.getCellFormula();
			break;
		case ERROR: //故障
			break;
		default:
			break;
		}
		return returnValue;
	}
}
