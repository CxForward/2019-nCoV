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

import cn.javaee.bean.Nation;
import cn.javaee.dao.NationDao;
import cn.javaee.dao.impl.NationDaoImpl;
import cn.javaee.web.NationWeb;

public class NationWebImpl implements NationWeb{

	NationDao ndi = new NationDaoImpl();
	Logger logger = Logger.getLogger(NationWebImpl.class);
	@Override
	public boolean insertByDate(String date) {
		String httpUrl = "http://api.tianapi.com/txapi/ncovabroad/index?key=e77495fe51167ea2facd2a088976d6ee&date=";
		JSONObject jsonResult = requestUrl(httpUrl, date);
		try {
			JSONArray jsonArray = jsonResult.getJSONArray("newslist");

			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				ndi.insertByAll(jsonObject.getString("provinceName"),
						jsonObject.getInteger("currentConfirmedCount"),
						jsonObject.getInteger("confirmedCount"),
						jsonObject.getInteger("curedCount"),
						jsonObject.getInteger("deadCount"),
						jsonObject.getString("continents"), date);
			}
			
			logger.info("全球数据添加成功："+date);
		
						// System.out.println(jsonObject.get("provinceName"));
		} catch (Exception e) {
			logger.info("添加：获取全球api数据失败  "+date);
			return false;
		}
		return true;
	}

	@Override
	public List<Nation> selectByDate(String date) {
		List<Nation> list = null;
		list = ndi.selectByDate(date);
		return list;
	}

	@Override
	public void updateByDate(String date) {
		String httpUrl = "http://api.tianapi.com/txapi/ncovabroad/index?key=e77495fe51167ea2facd2a088976d6ee&date=";
		JSONObject jsonResult = requestUrl(httpUrl, date);
		JSONArray jsonArray = jsonResult.getJSONArray("newslist");
		try {
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				ndi.updateByDate(jsonObject.getString("provinceName"),
						jsonObject.getInteger("currentConfirmedCount"),
						jsonObject.getInteger("confirmedCount"),
						jsonObject.getInteger("curedCount"),
						jsonObject.getInteger("deadCount"),
						jsonObject.getString("continents"), date);
				// System.out.println(jsonObject);
				// System.out.println(jsonObject.get("provinceName"));
			}
			logger.info("全球数据更新成功："+date);
			
		} catch (Exception e) {
			logger.info("更新：获取全球api数据失败  "+date);
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
