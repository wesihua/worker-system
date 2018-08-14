package com.wei.boot.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.util.DateUtils;

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

	@RequestMapping("/export")
	public void exportWord(HttpServletRequest request, HttpServletResponse response) {
		File file = createDoc();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			long fileLength = file.length();
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/msword");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("测试的.docx", "utf-8"));
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

	private File createDoc() {
		// 创建数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", "测试的标题");
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();// 这里是获取list列表的方法
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("code", "cityId");
		p1.put("name", "城市编号");
		p1.put("bt", "必填");
		p1.put("desc", "想休息休息休息");
		infoList.add(p1);

		dataMap.put("timeName", DateUtils.getCurTime());
		dataMap.put("infoList", infoList);

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
			t.process(dataMap, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return file;
	}

}
