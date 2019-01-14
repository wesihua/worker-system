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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.JobType;
import com.wei.boot.model.Result;
import com.wei.boot.model.Worker;
import com.wei.boot.model.excel.WorkerImportInfo;
import com.wei.boot.service.JobTypeService;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.CheckUtils;
import com.wei.boot.util.JsonUtil;
import com.wei.boot.util.ToolsUtil;

/**
 * 导入excel数据
 * 
 * @author weisihua 2018年7月30日 上午10:39:55
 */
@Controller
@RequestMapping("/excel")
public class ExcelImportController {

	public static final Logger log = LoggerFactory.getLogger(ExcelImportController.class);

	private static final String EXCEL_XLS = "xls";

	private static final String EXCEL_XLSX = "xlsx";

	@Autowired
	private WorkerService workerService;
	
	@Autowired
	private JobTypeService jobTypeService;

	@Value("${excel.upload.path}")
	private String importPath;

	@RequestMapping("/import")
	public String importExcel(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file,
			HttpServletResponse response) {
		Result result = Result.SUCCESS;
		Map<String, Object> map = new HashMap<>();
		int userId = ToolsUtil.getUserId(request);
		// 首先上传excel文件，后缀 .xls .xlsx
		String fileName = null;
		String filePath = importPath;
		try {
			fileName = ToolsUtil.get36UUID()
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
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
			result = Result.fail("excel文档上传失败");
			model.addAttribute("result", JsonUtil.bean2Json(result));
			return "import/importSuccess";
		} finally {
			File excelFile = null;
			try {
				List<WorkerImportInfo> infoList = new ArrayList<WorkerImportInfo>();
				// 解析excel文件并组装数据
				excelFile = new File(filePath + fileName); // 创建文件对象
				FileInputStream in = new FileInputStream(excelFile); // 文件流
				checkExcelVaild(excelFile);
				Workbook wb = WorkbookFactory.create(in);
				// sheet数量
				int sheetCount = wb.getNumberOfSheets();
				if (sheetCount > 0) {
					for (int i = 0; i < sheetCount; i++) {
						Sheet sheet = wb.getSheetAt(i);
						// 开始读取数据，excel的格式一定是固定的，否则组装的数据不对
						for (int j = 0; j <= sheet.getLastRowNum(); j++) {
							Row row = sheet.getRow(j);
							WorkerImportInfo info = new WorkerImportInfo();
							if (null != row) {
								if(row.getCell(0) == null || row.getCell(1) == null || row.getCell(4) == null
										|| StringUtils.isEmpty(getValue(row.getCell(0))) || StringUtils.isEmpty(getValue(row.getCell(1)))
										|| StringUtils.isEmpty(getValue(row.getCell(4)))) {
									continue;
								}
								if("姓名".equals(getValue(row.getCell(0)))) {
									continue;
								}
								info.setName(getValue(row.getCell(0)));
								info.setTelephone(getValue(row.getCell(1)));
								info.setEmail(getValue(row.getCell(2)));
								info.setIdcard(getValue(row.getCell(3)));
								info.setGender(getValue(row.getCell(4)));
								info.setWorkYear(getValue(row.getCell(5)));
								info.setDegree(getValue(row.getCell(6)));
								info.setMaritalStatus(getValue(row.getCell(7)));
								info.setPosition(getValue(row.getCell(8)));
								info.setTitle(getValue(row.getCell(9)));
								info.setAddress(getValue(row.getCell(10)));
								info.setProfile(getValue(row.getCell(11)));
								info.setDescription(getValue(row.getCell(12)));
								info.setBank(getValue(row.getCell(13)));
								info.setBankAccount(getValue(row.getCell(14)));
								info.setWorkStatusName(getValue(row.getCell(15)));
								infoList.add(info);
							}
						}
					}
				}
				// 将读取的信息转换成worker对象以便存入数据库
				List<Worker> workerList = new ArrayList<Worker>();
				List<WorkerImportInfo> existList = new ArrayList<WorkerImportInfo>();
				List<WorkerImportInfo> wrongIdList = new ArrayList<WorkerImportInfo>();
				List<WorkerImportInfo> wrongPhone = new ArrayList<WorkerImportInfo>();
				if (null != infoList && infoList.size() > 0) {
					for (WorkerImportInfo info : infoList) {
						if (StringUtils.isEmpty(info.getName()) || StringUtils.isEmpty(info.getTelephone()) || StringUtils.isEmpty(info.getGender())) {
							continue;
						}
						// 身份证号不正确的
						if (!StringUtils.isEmpty(info.getIdcard()) && !CheckUtils.isIdCard(info.getIdcard())) {
							wrongIdList.add(info);
							continue;
						}
						// 联系电话不正确
						/*
						if(!CheckUtils.isPhone(info.getTelephone()) && !CheckUtils.isMobile(info.getTelephone())) {
							wrongPhone.add(info);
							continue;
						}
						*/
						// 检查是否已存在
						if(!StringUtils.isEmpty(info.getIdcard())) {
							if (workerService.queryByIdcard(info.getIdcard())) {
								existList.add(info);
								continue;
							}
						}

						Worker worker = new Worker();
						worker.setName(info.getName());
						worker.setTelephone(info.getTelephone());
						worker.setEmail(info.getEmail());
						worker.setIdcard(info.getIdcard());
						worker.setTitle(info.getTitle());
						Integer gender = translateDic("gender", info.getGender());
						worker.setSex(gender);
						if (!StringUtils.isEmpty(info.getWorkYear())) {
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
						worker.setBank(info.getBank());
						worker.setBankAccount(info.getBankAccount());
						Integer workStatus = translateDic("workStatus", info.getWorkStatusName());
						worker.setWorkStatus(workStatus);
						worker.setSouce(GlobalConstant.Source.IMPORT);
						worker.setCreateTime(new Date());
						worker.setCreateUser(userId);
						workerList.add(worker);
					}
				}
				if(null != workerList && workerList.size() > 0) {
					workerService.insertBatch(workerList);
				}
				map.put("all", infoList.size());
				map.put("success", workerList.size());
				map.put("fail", infoList.size() - workerList.size());
				map.put("wrongIdcard", wrongIdList);
				map.put("wrongPhone", wrongPhone);
				map.put("exist", existList);
			} catch (Exception e) {
				log.error("excel文档解析失败", e);
				if(StringUtils.isEmpty(e.getMessage())) {
					result = Result.fail("excel文档解析失败！");
				}
				else {
					result = Result.fail(e.getMessage());
				}
				// 删除文件
				excelFile.delete();
				model.addAttribute("result", JsonUtil.bean2Json(result));
				return "import/importSuccess";
			}
			excelFile.delete();
		}
		result.setData(map);
		model.addAttribute("result", JsonUtil.bean2Json(result));
		return "import/importSuccess";
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

	
	private Integer getJobTypeId(String jobTypeName) {
		Integer id = null;
		JobType jobType = jobTypeService.queryByName(jobTypeName);
		if(null != jobType) {
			id = jobType.getId();
		}
		return id;
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
		if("workStatus".equals(type) && !StringUtils.isEmpty(text)) {
			if ("已入职工作".equals(text.trim())) {
				return 0;
			}
			if ("正在找工作".equals(text.trim())) {
				return 1;
			}
			if ("寻求更好的工作".equals(text.trim())) {
				return 2;
			}
			if ("寻求兼职".equals(text.trim())) {
				return 3;
			}
			if ("已离职".equals(text.trim())) {
				return 4;
			}
		}
		return null;
	}
	
}
