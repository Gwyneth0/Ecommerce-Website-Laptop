package com.poly.rest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Product;
import com.poly.service.ProductService;

@RestController
public class XcelRestController {

	@Autowired
	ProductService productService;
	
	@RequestMapping("/rest/export")
	public void print() {
		export();	
	}
	
	public void export() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("product");
		
			XSSFRow row = null;
			Cell cell = null;
			int numberRow = 2;
			
			// tạo tên table
			row = sheet.createRow(numberRow);//hàng bắt đầu đc tạo từ hàng sô mấy
			CellStyle styleNameTable = workbook.createCellStyle();
			createNameTable(workbook, sheet, row, cell, styleNameTable, 2, 2, 0, 5);
			
			//tạo tiêu đề cột
			row = sheet.createRow(++numberRow);
			CellStyle styleTitleCol = workbook.createCellStyle();
			styleTitleCol = setBorder(styleTitleCol);
			styleTitleCol.setFillBackgroundColor(IndexedColors.BLUE_GREY.index);
			styleTitleCol.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			String[] nameCol = {"STT", "Id", "Tên", "Ảnh", "Giá", "Ngày tạo"};
			createCol(sheet, workbook, row, cell, styleTitleCol, nameCol);
			
			// print data
			row = sheet.createRow(++numberRow);
			data(numberRow, sheet, workbook, row, cell);
			FileOutputStream out = new FileOutputStream(new File("/Users/duylnk/Desktop/demo.xlsx"));
        
			workbook.write(out);
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createCol(XSSFSheet sheet, XSSFWorkbook workbook, XSSFRow row, Cell cell, CellStyle style, String[] nameCol) {
		
		int cellStart = 0;// ô bắt đầu
		for (String name : nameCol) {
			cell = row.createCell(cellStart, CellType.STRING);
			cell.setCellValue(name);
			cell.setCellStyle(createCellStyle(workbook, style, (short)18, HSSFColorPredefined.LIGHT_BLUE.getIndex(), true, HorizontalAlignment.CENTER));
			sheet.autoSizeColumn(cellStart);// tự động chỉnh cột
			cellStart++;
		}
		
		
	}

	public CellStyle createCellStyle(Workbook workbook, CellStyle style, short fontSize, short color, boolean bold, HorizontalAlignment align) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBold(bold);
        font.setColor(color);
        
        style.setFont(font);
        style.setAlignment(align);
        return style;
    }
	
	public void createNameTable(Workbook workbook, XSSFSheet sheet,XSSFRow row, Cell cell,CellStyle style, int firstRow, int lastRow, int firstCol, int lastCol) {
		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue("Bảng sản phẩm");
		cell.setCellStyle(createCellStyle(workbook, style, (short)24, HSSFColorPredefined.BLACK.getIndex(), true, HorizontalAlignment.CENTER));
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));// gộp cột
	}
	
	
	public void data(int rowStart, XSSFSheet sheet, XSSFWorkbook workbook, XSSFRow row, Cell cell) {
		List<Product> list = productService.findAll();
		CreationHelper createHelper = workbook.getCreationHelper();
		DataFormat format = workbook.createDataFormat();
		
		int i = 0;
		int cellStart = 0;
		for (Product product : list) {
			row = sheet.createRow(rowStart);
			row.setHeight((short)2400);
			
			CellStyle styleStt = workbook.createCellStyle();
			styleStt = setBorder(styleStt);
			cell = row.createCell(cellStart, CellType.NUMERIC);
			cell.setCellValue(++i);
			cell.setCellStyle(createCellStyle(workbook, styleStt, (short)14, HSSFColorPredefined.BLACK.getIndex(), true, HorizontalAlignment.CENTER));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			CellStyle style = workbook.createCellStyle();
			style = setBorder(style);
			cell = row.createCell(cellStart, CellType.NUMERIC);
			cell.setCellValue(product.getId());
			cell.setCellStyle(createCellStyle(workbook, style, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.CENTER));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			CellStyle styleStr = workbook.createCellStyle();
			styleStr = setBorder(styleStr);
			cell = row.createCell(cellStart, CellType.STRING);
			cell.setCellValue(product.getName());
			cell.setCellStyle(createCellStyle(workbook, styleStr, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.LEFT));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			cell = row.createCell(cellStart, CellType.STRING);
			cell.setCellValue(product.getImage());
			cell.setCellStyle(createCellStyle(workbook, styleStr, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.LEFT));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			CellStyle stylePrice = workbook.createCellStyle();
			stylePrice = setBorder(stylePrice);
			stylePrice.setDataFormat(format.getFormat("#,##0.0"));
			cell = row.createCell(cellStart, CellType.NUMERIC);
			cell.setCellValue(product.getPrice());
			cell.setCellStyle(createCellStyle(workbook, stylePrice, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.RIGHT));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			CellStyle styleRowLast = workbook.createCellStyle();
			styleRowLast = setBorder(styleRowLast);
			cell = row.createCell(cellStart, CellType.STRING);
			styleRowLast.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			cell.setCellValue(product.getCreateDate());
			cell.setCellStyle(createCellStyle(workbook, styleRowLast, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.CENTER));
			sheet.autoSizeColumn(cellStart);
			cellStart++;
			
			try {
				cell = row.createCell(cellStart);
				
				cell.setCellStyle(createCellStyle(workbook, style, (short)13, HSSFColorPredefined.BLACK.getIndex(), false, HorizontalAlignment.CENTER));
				sheet.autoSizeColumn(cellStart);
				addImages(workbook, sheet, product.getImage());
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println(product.getImage());
				rowI ++;
			}
			cellStart=0;
			rowStart++;
			
		}
		rowI = 4;
	}
	
	
	public CellStyle setBorder(CellStyle cellStyle) {
		  cellStyle.setBorderBottom(BorderStyle.THIN);
		  cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  cellStyle.setBorderLeft(BorderStyle.THIN);
		  cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  cellStyle.setBorderRight(BorderStyle.THIN);
		  cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  cellStyle.setBorderTop(BorderStyle.THIN);
		  cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
		  return cellStyle;
	}
	
	
	int rowI = 4;
	public void addImages(XSSFWorkbook workbook, XSSFSheet sheet, String nameImage) throws FileNotFoundException, IOException {
		InputStream inputStream1 = null;
		byte[] inputImageBytes1 = null;
		try {
			 inputStream1 = new FileInputStream("./src/main/resources/static/assets/admin/ImageP/"+nameImage);
			 inputImageBytes1 = IOUtils.toByteArray(inputStream1);
		} catch (Exception e) {
			inputStream1 = new FileInputStream("./src/main/webapp/assets/admin/ImageP/"+nameImage);
			inputImageBytes1 = IOUtils.toByteArray(inputStream1);
			
		}

		int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, Workbook.PICTURE_TYPE_PNG);
		XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
		XSSFClientAnchor ironManAnchor = new XSSFClientAnchor();
		ironManAnchor.setCol1(6); 
		ironManAnchor.setCol2(8); 
		
		ironManAnchor.setRow1(rowI); 
		ironManAnchor.setRow2(rowI+1);
		rowI ++;
		drawing.createPicture(ironManAnchor, inputImagePictureID1);
	
		sheet.autoSizeColumn(3);

	}
	
}
