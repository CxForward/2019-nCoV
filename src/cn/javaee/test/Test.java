package cn.javaee.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class Test {
	static ProvinceWeb web = new ProvinceWebImpl();
	static NationWeb nw = new NationWebImpl();
	private static final long serialVersionUID = 1L;
	// Map<String,JSONObject> m = new HashMap<String, JSONObject>();
	// Map<String,JSONObject> forgin = new HashMap<String, JSONObject>();
	static List<List> chinaList = new ArrayList();
	static List<List> foreginList = new ArrayList();
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Long startTime = System.currentTimeMillis();
	static{
		List<String> daysList = getDays("2020-06-15", formatter.format(new Date()));
		for (String string : daysList) {
			List<Province> list1 = null;
			List<Nation> list2 = null;
			list1 = web.selectByDate(string);
			
			chinaList.add(list1);
			list2 = nw.selectByDate(string);
			//Object json2 = JSON.toJSON(list2);
			
			foreginList.add(list2);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void selectByDate() {
		System.out.println(chinaList.size());
		Long endTime = System.currentTimeMillis();
		if(endTime-startTime>1000*60*60){
			startTime = endTime;
			chinaList.remove(chinaList.size()-1);
			List<Province> list1 = null;
			list1 = web.selectByDate(formatter.format(new Date()));
			chinaList.add(list1);
			System.out.println("123");
		}
			String json =  JSON.toJSONString(chinaList);
			System.out.println(json);
			
		// String date = request.getParameter("date");
		/*
		 * if(m.get(date)!=null && !now.equals(date)){
		 * response.getWriter().print(m.get(date)); return; }
		 */
		
		
		
		
	//	list = web.selectByDate(date);
		
		// m.put(date, res);

		// response.getWriter().print(res);
	}

	public void selectByForeginDate() {
		Date d = new Date();
		String now = formatter.format(d);
		// String date = request.getParameter("date");
		/*
		 * if(forgin.get(date)!=null && !now.equals(date)){
		 * response.getWriter().print(forgin.get(date)); return; }
		 */
		List<Nation> list = null;
	//	list = nw.selectByDate(date);
		Object json = JSON.toJSON(list);
		JSONObject res = JSONObject.parseObject("{nationList:" + json + "}");
		// forgin.put(date, res);
		// response.getWriter().print(res);
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

	public static void main(String[] args) {
		Test t = new Test();
	//	t.selectByDate();
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(null);
		System.out.println(list);
		//System.out.println(startTime);
	//	System.out.println(endTime);
		/*while(endTime-startTime<1000*3){
			endTime = System.currentTimeMillis();
			System.out.println(startTime);
		}
		startTime = endTime;
		System.out.println(startTime);*/

	}

}
