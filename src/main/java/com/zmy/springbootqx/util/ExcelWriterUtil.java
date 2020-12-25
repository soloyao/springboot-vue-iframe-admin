package com.zmy.springbootqx.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.zmy.springbootqx.pojo.Hero;

/**
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: ExcelWriterUtil.java
 * @Description: 生成Excel并写入数据
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年12月25日 下午1:56:25
 */
public class ExcelWriterUtil {

	/**
	 * 生成Excel并写入数据，导出
	 * @param heroList
	 * @param cells
	 * @return
	 */
	public static Result exportData(List<Hero> heroList, String[] cells) {
		//生成xlsx的Excel工作簿对象
		Workbook workbook = new SXSSFWorkbook();
		//生成Sheet表，写入第一行的列头
		Sheet sheet = buildDataSheet(workbook, cells);
		//构建每行的数据内容
		int rowNum = 1;
		for (Iterator<Hero> it = heroList.iterator(); it.hasNext();) {
			Hero hero = it.next();
			if (hero == null) {
				continue;
			}
			//输出行数据
			Row row = sheet.createRow(rowNum++);
			convertDataToRow(hero, row);
		}
		
		FileOutputStream fileOutputStream = null;
		try {
			String parentFilePath = "D:/springbootUpload";
			String exportFilePath = parentFilePath + "/" + System.currentTimeMillis() + "英雄.xlsx";
			File parentFile = new File(parentFilePath);
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			File exportFile = new File(exportFilePath);
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}
			
			fileOutputStream = new FileOutputStream(exportFile);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			return Result.success("导出数据成功，导出文件路径为" + exportFilePath + "，可前往查看");
		} catch (Exception e) {
			return Result.fail("导出Excel时发生错误，错误原因：" + e.getMessage());
		} finally {
			try {
				if (null != fileOutputStream) {
					fileOutputStream.close();
				}
				if (null != workbook) {
					workbook.close();
				}
			} catch (Exception e) {
				return Result.fail("导出Excel时发生错误，错误原因：" + e.getMessage());
			}
		}
	}
	
	/**
	 * 生成Sheet表，并写入第一行数据（列头）
	 * @param workbook
	 * @param cells
	 * @return
	 */
	private static Sheet buildDataSheet(Workbook workbook, String[] cells) {
		Sheet sheet = workbook.createSheet();
		//设置列头宽度
		for (int i = 0; i < cells.length; i++) {
			sheet.setColumnWidth(i, 4000);
		}
		//设置默认行高
		sheet.setDefaultRowHeight((short) 400);
		//构建头单元格样式
		CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
		//写入第一行各列的数据
		Row head = sheet.createRow(0);
		for (int i = 0; i < cells.length; i++) {
			Cell cell = head.createCell(i);
			cell.setCellValue(cells[i]);
			cell.setCellStyle(cellStyle);
		}
		return sheet;
	}
	
	/**
	 * 设置第一行列头的样式
	 * @param workbook 工作簿对象
	 * @return 单元格样式对象
	 */
	private static CellStyle buildHeadCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		//对齐方式设置
		style.setAlignment(HorizontalAlignment.CENTER);
		//边框颜色和宽度设置
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());//下边框
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());//左边框
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());//右边框
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());//上边框
		//设置背景颜色
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//粗体字设置
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		return style;
	}
	
	/**
	 * 将数据转换成行
	 * @param data 源数据
	 * @param row 行对象
	 */
	private static void convertDataToRow(Hero hero, Row row) {
		int cellNum = 0;
		Cell cell;
		//名称
		cell = row.createCell(cellNum++);
		cell.setCellValue(null == hero.getName() ? "" : hero.getName());
		//血量
		cell = row.createCell(cellNum++);
		if (null != hero.getHp()) {
			cell.setCellValue(hero.getHp());
		} else {
			cell.setCellValue("");
		}
	}
	
}
