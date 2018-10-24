package com.wei.boot.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wei.boot.mapper.DemandOrderMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.report.ReportInfo;
import com.wei.boot.service.ReportService;
import com.wei.boot.util.DateUtils;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private DemandOrderMapper demandOrderMapper;
	
	@Autowired
	private WorkerMapper workerMapper;
	
	@Override
	public int queryOrderCountByTime(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
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
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
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
			return demandOrderMapper.selectIncomeByTime(map);
		}
		return null;
	}

	@Override
	public BigDecimal queryAllIncome() {
		return demandOrderMapper.selectAllIncome();
	}

	@Override
	public int queryOrderWorkerCountByTime(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
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

	@Override
	public List<ReportInfo> queryWorkerSourcePie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectSourcePie(map);
	}

	@Override
	public List<ReportInfo> queryWorkerCreateUserPie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectCreateUserPie(map);
	}

	@Override
	public List<ReportInfo> queryWorkerDegreePie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectDegreePie(map);
	}

	@Override
	public List<ReportInfo> queryWorkerMonthBar(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectMonthBar(map);
	}

	@Override
	public List<ReportInfo> queryDemandMonthBar(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectDemandMonthBar(map);
	}

	@Override
	public List<ReportInfo> queryDemandUnderTakerPie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectDemandUnderTakerPie(map);
	}

	@Override
	public List<ReportInfo> queryDemandStatePie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectDemandStatePie(map);
	}

	@Override
	public List<ReportInfo> queryOrderMonthBar(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderMonthBar(map);
	}

	@Override
	public List<ReportInfo> queryOrderUndertakerPie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderUndertakerPie(map);
	}

	@Override
	public List<ReportInfo> queryOrderMemberMonthBar(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderMemberMonthBar(map);
	}

	@Override
	public List<ReportInfo> queryOrderMemberUndertakerPie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderMemberUndertakerPie(map);
	}

	@Override
	public List<ReportInfo> queryOrderIncomeMonthBar(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderIncomeMonthBar(map);
	}

	@Override
	public List<ReportInfo> queryOrderIncomeUndertakerPie(String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date beginTime = null;
		Date endTime = null;
		if(!StringUtils.isEmpty(beginDate)) {
			beginTime = DateUtils.parseDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		}
		if(!StringUtils.isEmpty(endDate)) {
			endTime = DateUtils.parseDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return workerMapper.selectOrderIncomeUndertakerPie(map);
	}

}
