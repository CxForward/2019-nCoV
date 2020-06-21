package cn.javaee.servletListener.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import cn.javaee.bean.Nation;
import cn.javaee.bean.Province;
import cn.javaee.web.NationWeb;
import cn.javaee.web.ProvinceWeb;
import cn.javaee.web.impl.NationWebImpl;
import cn.javaee.web.impl.ProvinceWebImpl;

public class StatisticsTask extends TimerTask {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private Date d = new Date();
	private static boolean china_flag = false;
	private static boolean global_flag = false;
	private String tag = df.format(d);//标志：用来判断是否进行新的一天
	private ProvinceWeb pw = new ProvinceWebImpl();
	private NationWeb nw = new NationWebImpl();
	
	Logger logger = Logger.getLogger(StatisticsTask.class);
	// @Override
	/*
	 * public void run() { System.out.println("123"); Calendar cal =
	 * Calendar.getInstance(); // System.out.println(china_flag); if (!china_flag)
	 * { if (STATISTICS_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) //
	 * 查看是否为凌晨 { china_flag = true; context.log("开始执行指定任务");
	 * 
	 * // TODO 添加自定义的详细任务 executeTask();
	 * 
	 * // 指定任务执行结束 china_flag = false; context.log("指定任务执行结束"); } } else {
	 * context.log("上一次任务执行还未结束"); }
	 * 
	 * }
	 */
	@Override
	public void run() {
		executeTask();
	}

	/**
	 * 执行任务
	 * 
	 */
	public void executeTask() {
		//SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String now = df.format(date);
		//String now1 = df1.format(date);
		logger.info("执行任务");
		if (now.equals(tag) == false) {
			logger.info("新的一天");
			tag = now;
			china_flag = false;
			global_flag = false;
		}
		
		// 判断是否需要插入当天的数据
		if (!china_flag) {
			List<Province> list1 = pw.selectByDate(now);
			if (list1.size() == 0){
				logger.info("添加国内数据，日期："+now);
				china_flag = pw.insertByDate(now);// 根据查询的结果决定是否需要继续执行插入当天数据的操作（插入失败时会继续尝试插入当天的数据，直到插入成功为止）
			}
				else{
				china_flag = true;
				pw.updateByDate(getLastDay(now));
			}

		} else {
			logger.info("更新国内数据，日期："+now);
			pw.updateByDate(now);
		}
		
		if (!global_flag) {
			List<Nation> list2 = nw.selectByDate(now);
			if (list2.size() == 0){
				logger.info("添加全球数据，日期："+now);
				global_flag = nw.insertByDate(now);// 根据查询的结果决定是否需要继续执行插入当天数据的操作（插入失败时会继续尝试插入当天的数据，直到插入成功为止）
			}
				else{
				global_flag= true;
				nw.updateByDate(getLastDay(now));
			}

		} else {
			logger.info("更新全球数据：日期"+now);
			nw.updateByDate(now);
		}
	}
	/**
	 * 获取上一天日期
	 * @param time
	 * @return
	 */
	public String getLastDay(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		// 此处修改为+1则是获取后一天 ,-1则是获取前一天
		calendar.set(Calendar.DATE, day - 1);

		String lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}
}
