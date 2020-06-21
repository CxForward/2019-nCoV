package cn.javaee.test;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.javaee.bean.Province;
import cn.javaee.web.ProvinceWeb;
import cn.javaee.web.impl.ProvinceWebImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestChina {
	static ProvinceWeb web = new ProvinceWebImpl();

	protected void insertByDate(String date) {
		web.insertByDate(date);
		// response.getWriter().print(result);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void selectByDate(String date) {
		List<Province> list = null;
		list = web.selectByDate(date);
		Object json =  JSON.toJSON(list);
		JSONObject res = JSONObject.parseObject("{provinceList:"+json+"}");
		System.out.println(res);
		//Object json =  JSON.toJSON(p);
		//JSONObject result = JSONObject.fromObject(json);
		
	}

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String[] dates = new String[]{"2020-06-10","2020-06-11","2020-06-12"};
		for (String string : dates) {
			System.out.println(string);
			web.insertByDate(string);
		}
		// t.insetByDate("2020-04-28");
		//t.insertByDate("2020-04-29");
	}
}
