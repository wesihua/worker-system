package com.wei.boot.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wei.boot.mapper.DemandOrderMapper;
import com.wei.boot.service.ReportService;
import com.wei.boot.util.DateUtils;

@Service
public class ReportServiceImple implements ReportService {

	@Autowired
	private DemandOrderMapper demandOrderMapper;
	
	
	@Override
	public int queryOrderCountByTime(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				try {
					map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if("week".equals(flag)) {
				cal.add(Calendar.DAY_OF_MONTH, -7);
				map.put("beginTime", cal.getTime());
			}
			else if("oneMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -1);
				map.put("beginTime", cal.getTime());
			}
			else if("threeMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -3);
				map.put("beginTime", cal.getTime());
			}
			else if("year".equals(flag)) {
				cal.add(Calendar.YEAR, -1);
				map.put("beginTime", cal.getTime());
			}
			return demandOrderMapper.selectOrderCountByTime(map);
		}
		return 0;
	}

	@Override
	public int queryAllOrderCount() {
		return demandOrderMapper.selectAllOrderCount();
	}

	@Override
	public BigDecimal queryIncomeByTime(String flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal queryAllIncome() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryOrderWorkerCountByTime(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				try {
					map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if("week".equals(flag)) {
				cal.add(Calendar.DAY_OF_MONTH, -7);
				map.put("beginTime", cal.getTime());
			}
			else if("oneMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -1);
				map.put("beginTime", cal.getTime());
			}
			else if("threeMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -3);
				map.put("beginTime", cal.getTime());
			}
			else if("year".equals(flag)) {
				cal.add(Calendar.YEAR, -1);
				map.put("beginTime", cal.getTime());
			}
			return demandOrderMapper.selectOrderWorkerCountByTime(map);
		}
		return 0;
	}

	@Override
	public int queryAllOrderWorkerCount() {
		return demandOrderMapper.selectAllOrderWorkerCount();
	}

}
