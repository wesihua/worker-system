package com.wei.boot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wei.boot.model.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 导入excel数据
 * @author weisihua
 * 2018年7月30日 上午10:39:55
 */
@Api(value = "excel导入导出相关接口")
@RestController
@RequestMapping("/excel")
public class ExcelImportController {
	
	public static final Logger log = LoggerFactory.getLogger(ExcelImportController.class);
	
	private static final String EXCEL_XLS = "xls";
	
    private static final String EXCEL_XLSX = "xlsx";
    
    @Value("${excel.upload.path}")
    private String import_path;
	
    @ApiOperation(value = "导入",notes = "")
	@RequestMapping("/import")
	public Result importExcel(HttpServletRequest request,@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		
			// 首先上传excel文件，后缀 .xls .xlsx
			String fileName = null;
			String filePath = import_path;
			try {
				fileName = file.getOriginalFilename();
				// 该路径固定
				//filePath = "/Users/weisihua/excel_import/";
				File targetFile = new File(filePath);  
				if(!targetFile.exists()){    
				    targetFile.mkdirs();    
				}       
				FileOutputStream out = new FileOutputStream(filePath+fileName);
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("excel文档上传失败", e);
				return Result.fail("excel文档上传失败");
			}
			finally {
				try {
					// 解析excel文件并组装数据
					File excelFile = new File(filePath+fileName); // 创建文件对象  
					FileInputStream in = new FileInputStream(excelFile); // 文件流  
					checkExcelVaild(excelFile);
					Workbook wb = WorkbookFactory.create(in);
					// sheet数量
					int sheetCount = wb.getNumberOfSheets();
					if(sheetCount > 0) {
						for(int i=0; i<sheetCount; i++) {
							Sheet sheet = wb.getSheetAt(i);
							// 开始读取数据，excel的格式一定是固定的，否则组装的数据不对
							for(int j=0; j<sheet.getLastRowNum(); j++) {
								Row row = sheet.getRow(j);
								System.out.println("\n");
								if(null != row) {
									int columnCount = row.getLastCellNum();
									for(int index=0; index<columnCount; index++) {
										Cell cell = row.getCell(index);
										if(null != cell) {
											String value = getValue(cell);
											System.out.print(value + ",\t");
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("excel文档解析失败", e);
					return Result.fail(e);
				}
			}
			return Result.SUCCESS;
	}
	
	/** 
     * 判断文件是否是excel 
     * @throws Exception  
     */  
    public static void checkExcelVaild(File file) throws Exception{  
        if(!file.exists()){  
            throw new Exception("文件不存在");  
        }  
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){  
            throw new Exception("文件不是Excel");  
        }  
    }
    
	private static String getValue(Cell cell) {
		String str = null;
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			str = String.valueOf(cell.getNumericCellValue());
			break;
		case STRING:
			str = cell.getStringCellValue();
			break;
		default:
			break;
		}
		return str;
	}

}
