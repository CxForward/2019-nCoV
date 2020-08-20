package cn.javaee.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.javaee.bean.Nation;
import cn.javaee.bean.Province;
import cn.javaee.web.NationWeb;
import cn.javaee.web.ProvinceWeb;
import cn.javaee.web.impl.NationWebImpl;
import cn.javaee.web.impl.ProvinceWebImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@WebServlet("/DataApi")
public class DataApi extends BaseServlet {
	static ProvinceWeb web = new ProvinceWebImpl();
	static NationWeb nw = new NationWebImpl();
	private static final long serialVersionUID = 1L;
	// Map<String,JSONObject> m = new HashMap<String, JSONObject>();
	// Map<String,JSONObject> forgin = new HashMap<String, JSONObject>();
	static List<List> chinaList = new ArrayList();
	static List<List> foreginList = new ArrayList();
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// data表示当天日期
	static String date = formatter.format(new Date());
	Long startTime = System.currentTimeMillis();
	static {
		List<String> daysList = getDays("2020-06-15",
				formatter.format(new Date()));
		for (String string : daysList) {
			List<Province> list1 = null;
			List<Nation> list2 = null;
			list1 = web.selectByDate(string);
			chinaList.add(list1);
			list2 = nw.selectByDate(string);
			foreginList.add(list2);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void selectByDate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Long endTime = System.currentTimeMillis();
		if (endTime - startTime > 1000 * 60 * 60) {
			startTime = endTime;
			Date d = new Date();
			String now = formatter.format(d);
			List<Province> list1 = null;
			List<Nation> list2 = null;
			list1 = web.selectByDate(date);
			list2 = nw.selectByDate(date);
			//判断能否获取到date的数据，能获取才进行移除和添加操作
			if (list1 != null && list1.size() > 0) {
				System.out.println("更新list国内数据");
				chinaList.remove(chinaList.size() - 1);
				chinaList.add(list1);
			}
			if (list2 != null && list2.size() > 0) {
				System.out.println("更新list国外数据");
				foreginList.remove(foreginList.size() - 1);
				foreginList.add(list2);
			}
			if (!now.equals(date)) {
				System.out.println("新的一天了...");
				date = now;
				list1 = web.selectByDate(date);
				list2 = nw.selectByDate(date);
				chinaList.add(list1);
				foreginList.add(list2);
			}
			System.out.println("123");
		}
		String json = JSON.toJSONString(chinaList);

		response.getWriter().print(json);
	}

	public void selectByForeginDate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String json = JSON.toJSONString(foreginList);

		response.getWriter().print(json);
		/*
		 * Date d = new Date(); String now = formatter.format(d); String date =
		 * request.getParameter("date");
		 * 
		 * if(forgin.get(date)!=null && !now.equals(date)){
		 * response.getWriter().print(forgin.get(date)); return; }
		 * 
		 * List<Nation> list = null; list = nw.selectByDate(date); Object json =
		 * JSON.toJSON(list); JSONObject res =
		 * JSONObject.parseObject("{nationList:" + json + "}"); //
		 * forgin.put(date, res); response.getWriter().print(res);
		 */
	}

	public static List<String> getDays(String startTime, String endTime) {

		// 返回的日期集合
		List<String> days = new ArrayList<String>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

}
