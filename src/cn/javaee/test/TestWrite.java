package cn.javaee.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.javaee.bean.Province;
import cn.javaee.dao.impl.ProvinceDaoImpl;

public class TestWrite {

	public static void main(String[] args) {
		ProvinceDaoImpl pdi = new ProvinceDaoImpl();
		String httpUrl = "http://api.tianapi.com/txapi/ncovcity/index?key=e77495fe51167ea2facd2a088976d6ee&date=";
		JSONObject jsonResult = request(httpUrl, "2020-04-28");
		JSONArray jsonArray = jsonResult.getJSONArray("newslist");
		Province p = null;
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			pdi.insertByAll(jsonObject.getString("provinceName"),
					jsonObject.getString("provinceShortName"),
					jsonObject.getInteger("currentConfirmedCount"),
					jsonObject.getInteger("confirmedCount"),
					jsonObject.getInteger("suspectedCount"),
					jsonObject.getInteger("curedCount"), jsonObject.getInteger("deadCount"),
					jsonObject.getString("comment"), "2020-04-28");
			System.out.println(jsonObject);
			// System.out.println(jsonObject.get("provinceName"));
		}
	}

	public static JSONObject request(String httpUrl, String httpArg) {
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
