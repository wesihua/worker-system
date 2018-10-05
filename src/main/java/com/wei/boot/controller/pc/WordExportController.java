package com.wei.boot.controller.pc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerEducation;
import com.wei.boot.model.WorkerExperience;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ToolsUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * word导出controller
 * 
 * @author weisihua 2018年8月1日 下午5:33:45
 */
@RestController
@RequestMapping("/word")
public class WordExportController {

	public static final Logger log = LoggerFactory.getLogger(WordExportController.class);

	@Autowired
	private WorkerService workerService;
	
	@RequestMapping("/export")
	public void exportWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String workerId = request.getParameter("workerId");
		Worker worker = workerService.queryDetail(Integer.parseInt(workerId));
		File file = createDoc(worker);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String fileName = "简历-"+worker.getName()+".docx";
			long fileLength = file.length();
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/msword");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 删除临时生成的word文件
			file.delete();
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private File createDoc(Worker worker) throws Exception {
		if(null != worker) {
			if(null != worker.getBirthday()) {
				worker.setBirthdayName(DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd"));
			}
			ToolsUtil.reflectDefaultValue(worker);
			if(null != worker.getEducationList() && worker.getEducationList().size() > 0) {
				for(WorkerEducation edu : worker.getEducationList()) {
					if(null != edu.getBeginTime()) {
						edu.setBeginTimeName(DateUtils.formatDate(edu.getBeginTime(), "yyyy-MM-dd"));
					}
					if(null != edu.getEndTime()) {
						edu.setEndTimeName(DateUtils.formatDate(edu.getEndTime(), "yyyy-MM-dd"));
					}
					
					ToolsUtil.reflectDefaultValue(edu);
				}
			}
			if(null != worker.getExperienceList() && worker.getExperienceList().size() > 0) {
				for(WorkerExperience exp : worker.getExperienceList()) {
					if(null != exp.getBeginTime()) {
						exp.setBeginTimeName(DateUtils.formatDate(exp.getBeginTime(), "yyyy-MM-dd"));
					}
					if(null != exp.getEndTime()) {
						exp.setEndTimeName(DateUtils.formatDate(exp.getEndTime(), "yyyy-MM-dd"));
					}
					ToolsUtil.reflectDefaultValue(exp);
				}
			}
		}
		
		// 获取模板
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/tpl");
		Template t = null;
		// 临时生成word文件，用于文件流下载，下载完成会删掉
		String name = "temp" + (int) (Math.random() * 1000) + ".docx";
		File file = new File(name);
		try {
			t = configuration.getTemplate("word_template.xml");
			// t.setEncoding("UTF-8");

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), "UTF-8"));
			t.process(worker, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return file;
	}

}
