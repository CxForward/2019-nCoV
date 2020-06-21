package cn.javaee.service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.javaee.bean.Nation;
import cn.javaee.bean.Province;
import cn.javaee.web.NationWeb;
import cn.javaee.web.ProvinceWeb;
import cn.javaee.web.impl.NationWebImpl;
import cn.javaee.web.impl.ProvinceWebImpl;
@WebServlet("/DataApi")
public class DataApi extends BaseServlet {
	ProvinceWeb web = new ProvinceWebImpl();
	NationWeb nw = new NationWebImpl();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void selectByDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		//System.out.println(date);
		List<Province> list = null;
		list = web.selectByDate(date);
		Object json =  JSON.toJSON(list);
		JSONObject res = JSONObject.parseObject("{provinceList:"+json+"}");
		response.getWriter().print(res);
	}
	public void selectByForeginDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		//System.out.println(date);
		List<Nation> list = null;
		list = nw.selectByDate(date);
		Object json =  JSON.toJSON(list);
		JSONObject res = JSONObject.parseObject("{nationList:"+json+"}");
		response.getWriter().print(res);
	}
	
	
}

