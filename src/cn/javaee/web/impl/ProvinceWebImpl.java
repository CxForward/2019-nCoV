package cn.javaee.web.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.javaee.bean.Province;
import cn.javaee.dao.ProvinceDao;
import cn.javaee.dao.impl.ProvinceDaoImpl;
import cn.javaee.web.ProvinceWeb;

public class ProvinceWebImpl implements ProvinceWeb {
	ProvinceDao pdi = new ProvinceDaoImpl();
	Logger logger = Logger.getLogger(ProvinceWebImpl.class);
	@Override
	public boolean insertByDate(String date) {
		String httpUrl = "http://api.tianapi.com/txapi/ncovcity/index?key=e77495fe51167ea2facd2a088976d6ee&date=";
		JSONObject jsonResult = requestUrl(httpUrl, date);
		try {
			JSONArray jsonArray = jsonResult.getJSONArray("newslist");
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				pdi.insertByAll(jsonObject.getString("provinceName"),
						jsonObject.getString("provinceShortName"),
						jsonObject.getInteger("currentConfirmedCount"),
						jsonObject.getInteger("confirmedCount"),
						jsonObject.getInteger("suspectedCount"),
						jsonObject.getInteger("curedCount"),
						jsonObject.getInteger("deadCount"),
						jsonObject.getString("comment"), date);
			}
			logger.info("国内数据添加成功："+date);
		
			// System.out.println(jsonObject);
			// System.out.println(jsonObject.get("provinceName"));
		} catch (Exception e) {
			logger.info("添加：获取国内api数据失败  "+date);
			return false;
		}
		return true;
	}

	@Override
	public List<Province> selectByDate(String date) {
		List<Province> list = null;
		list = pdi.selectByDate(date);
		return list;
	}

	@Override
	public void updateByDate(String date) {
		String httpUrl = "http://api.tianapi.com/txapi/ncovcity/index?key=e77495fe51167ea2facd2a088976d6ee&date=";
		JSONObject jsonResult = requestUrl(httpUrl, date);
		JSONArray jsonArray = jsonResult.getJSONArray("newslist");
		try {
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				pdi.updateByDate(jsonObject.getString("provinceName"),
						jsonObject.getString("provinceShortName"),
						jsonObject.getInteger("currentConfirmedCount"),
						jsonObject.getInteger("confirmedCount"),
						jsonObject.getInteger("suspectedCount"),
						jsonObject.getInteger("curedCount"),
						jsonObject.getInteger("deadCount"),
						jsonObject.getString("comment"), date);
				// System.out.println(jsonObject);
				// System.out.println(jsonObject.get("provinceName"));
			}
			logger.info("国内数据更新成功："+date);
			
		} catch (Exception e) {
			logger.info("更新：获取国内api数据失败  "+date);
		}
	}

	@Override
	public JSONObject requestUrl(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.parseObject(result);

		return json;
	}

}
