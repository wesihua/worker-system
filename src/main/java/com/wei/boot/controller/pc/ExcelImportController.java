package com.wei.boot.controller.pc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Result;
import com.wei.boot.model.Worker;
import com.wei.boot.model.excel.WorkerImportInfo;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.CheckUtils;
import com.wei.boot.util.ToolsUtil;

/**
 * 导入excel数据
 * 
 * @author weisihua 2018年7月30日 上午10:39:55
 */
@RestController
@RequestMapping("/excel")
public class ExcelImportController {

	public static final Logger log = LoggerFactory.getLogger(ExcelImportController.class);

	private static final String EXCEL_XLS = "xls";

	private static final String EXCEL_XLSX = "xlsx";

	@Autowired
	private WorkerService workerService;

	@Value("${excel.upload.path}")
	private String import_path;

	@RequestMapping("/import")
	public Result importExcel(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = ToolsUtil.getUserId(request);
		// 首先上传excel文件，后缀 .xls .xlsx
		String fileName = null;
		String filePath = import_path;
		try {
			fileName = ToolsUtil.get36UUID()
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			// 该路径固定
			filePath = "/Users/weisihua/excel_import/";
			File targetFile = new File(filePath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(filePath + fileName);
			out.write(file.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("excel文档上传失败", e);
			return Result.fail("excel文档上传失败！");
		} finally {
			try {
				List<WorkerImportInfo> infoList = new ArrayList<WorkerImportInfo>();
				// 解析excel文件并组装数据
				File excelFile = new File(filePath + fileName); // 创建文件对象
				FileInputStream in = new FileInputStream(excelFile); // 文件流
				checkExcelVaild(excelFile);
				Workbook wb = WorkbookFactory.create(in);
				// sheet数量
				int sheetCount = wb.getNumberOfSheets();
				if (sheetCount > 0) {
					for (int i = 0; i < sheetCount; i++) {
						Sheet sheet = wb.getSheetAt(i);
						// 开始读取数据，excel的格式一定是固定的，否则组装的数据不对
						for (int j = 1; j < sheet.getLastRowNum(); j++) {
							Row row = sheet.getRow(j);
							WorkerImportInfo info = new WorkerImportInfo();
							if (null != row) {
								info.setName(getValue(row.getCell(0)));
								info.setTelephone(getValue(row.getCell(1)));
								info.setEmail(getValue(row.getCell(2)));
								info.setIdcard(getValue(row.getCell(3)));
								info.setGender(getValue(row.getCell(4)));
								info.setWorkYear(getValue(row.getCell(5)));
								info.setDegree(getValue(row.getCell(6)));
								info.setMaritalStatus(getValue(row.getCell(7)));
								info.setPosition(getValue(row.getCell(8)));
								info.setAddress(getValue(row.getCell(9)));
								info.setProfile(getValue(row.getCell(10)));
								info.setDescription(getValue(row.getCell(11)));
								infoList.add(info);
							}
						}
					}
				}
				// 将读取的信息转换成worker对象以便存入数据库
				List<Worker> workerList = new ArrayList<Worker>();
				List<String> existList = new ArrayList<String>();
				List<String> wrongIdList = new ArrayList<String>();
				List<String> addressList = new ArrayList<String>();
				List<String> descList = new ArrayList<String>();
				if (null != infoList && infoList.size() > 0) {
					for (WorkerImportInfo info : infoList) {
						if (StringUtils.isEmpty(info.getName()) || StringUtils.isEmpty(info.getTelephone())
								|| StringUtils.isEmpty(info.getIdcard()) || StringUtils.isEmpty(info.getGender())) {
							continue;
						}
						// 身份证号不正确的
						if (!CheckUtils.isIdCard(info.getIdcard())) {
							wrongIdList.add(info.getIdcard());
							continue;
						}
						// 检查是否已存在
						if (workerService.queryByIdcard(info.getIdcard())) {
							existList.add(info.getIdcard());
							continue;
						}
						// 地址长度超过50的
						if (null != info.getAddress() && info.getAddress().length() > 50) {
							addressList.add(info.getIdcard());
							continue;
						}
						// 个人简介和备注说明超过255的
						if (null != info.getProfile() && info.getProfile().length() > 255) {
							descList.add(info.getIdcard());
							continue;
						}
						if (null != info.getDescription() && info.getDescription().length() > 255) {
							descList.add(info.getIdcard());
							continue;
						}

						Worker worker = new Worker();
						worker.setName(info.getName());
						worker.setTelephone(info.getTelephone());
						worker.setEmail(info.getEmail());
						worker.setIdcard(info.getIdcard());
						Integer gender = translateDic("gender", info.getGender());
						worker.setSex(gender);
						if (null != info.getWorkYear()) {
							worker.setWorkYear(Integer.parseInt(info.getWorkYear()));
						}
						Integer degree = translateDic("degree", info.getDegree());
						worker.setDegree(degree);
						Integer maritalStatus = translateDic("maritalStatus", info.getMaritalStatus());
						worker.setMaritalStatus(maritalStatus);
						worker.setPosition(info.getPosition());
						worker.setAddress(info.getAddress());
						worker.setProfile(info.getProfile());
						worker.setDescription(info.getDescription());
						worker.setSouce(GlobalConstant.Source.IMPORT);
						worker.setCreateTime(new Date());
						worker.setCreateUser(userId);
						workerList.add(worker);
					}
				}
				if(null != workerList && workerList.size() > 0) {
					//workerService.insertBatch(workerList);
				}
				map.put("all", infoList.size());
				map.put("success", workerList.size());
				map.put("fail", infoList.size() - workerList.size());
				map.put("wrongIdcard", wrongIdList);
				map.put("exist", existList);
				map.put("address", addressList);
				map.put("desc", descList);
			} catch (Exception e) {
				log.error("excel文档解析失败", e);
				return Result.fail("excel文档解析失败！");
			}
		}
		return Result.success(map);
	}

	/**
	 * 判断文件是否是excel
	 * 
	 * @throws Exception
	 */
	public static void checkExcelVaild(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("文件不存在");
		}
		if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
			throw new Exception("文件不是Excel");
		}
	}

	private static String getValue(Cell cell) {
		String str = null;
		if (null != cell) {
			switch (cell.getCellTypeEnum()) {
			case NUMERIC:
				str = String.valueOf((int) cell.getNumericCellValue());
				break;
			case STRING:
				str = cell.getStringCellValue();
				break;
			default:
				break;
			}
		}
		return str;
	}

	/**
	 * 翻译字段字段
	 * 
	 * @param type
	 * @param text
	 * @return
	 */
	private static Integer translateDic(String type, String text) {
		if ("gender".equals(type) && !StringUtils.isEmpty(text)) {
			if ("男".equals(text.trim())) {
				return 0;
			} else if ("女".equals(text.trim())) {
				return 1;
			}
		}
		if ("degree".equals(type) && !StringUtils.isEmpty(text)) {
			if ("小学".equals(text.trim())) {
				return 6;
			}
			if ("初中".equals(text.trim())) {
				return 7;
			}
			if ("高中".equals(text.trim())) {
				return 8;
			}
			if ("大专".equals(text.trim())) {
				return 1;
			}
			if ("本科".equals(text.trim())) {
				return 2;
			}
			if ("硕士".equals(text.trim())) {
				return 3;
			}
			if ("博士".equals(text.trim())) {
				return 4;
			}
			if ("其他".equals(text.trim())) {
				return 5;
			}
		}
		if ("maritalStatus".equals(type) && !StringUtils.isEmpty(text)) {
			if ("未婚".equals(text.trim())) {
				return 0;
			}
			if ("已婚".equals(text.trim())) {
				return 1;
			}
			if ("离异".equals(text.trim())) {
				return 2;
			}
			if ("丧偶".equals(text.trim())) {
				return 3;
			}
		}
		return null;
	}
}
