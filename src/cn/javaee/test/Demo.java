package cn.javaee.test;

import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

public class Demo {
	public void resChinaData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	
		String httpUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
		String res = requestUrl(httpUrl, "");
		 res = StringEscapeUtils.unescapeJavaScript(res);
		System.out.println(res);
		
	}
	
	public void resTianForeginDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String httpUrl = "http://api.tianapi.com/txapi/ncovabroad/index?";
		String res = requestUrl(httpUrl, "key=e77495fe51167ea2facd2a088976d6ee");
		
		System.out.println(res);
	}
	public static void main(String[] args) throws ServletException, IOException {
		Demo d = new Demo();
		HttpServletRequest request = null;
		HttpServletResponse response =null;
		//d.resChinaData(request, response);
		d.resTianForeginDate(request, response);
	}
	
	public String requestUrl(String httpUrl, String httpArg) {
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
		return result;
	}
}
