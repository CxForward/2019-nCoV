package cn.javaee.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;



@WebServlet("/TianData")
public class TianData extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 获取国内数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void resChinaData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter writer = response.getWriter();
		String httpUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
		String res = requestUrl(httpUrl, "");
		res = StringEscapeUtils.unescapeJavaScript(res);
		int cnt = res.lastIndexOf("\"");
		StringBuffer result = new StringBuffer(res);
		result = result.deleteCharAt(16);
		result = result.deleteCharAt(cnt-1);
		writer.print(result);
	}
	
	/**
	 * 获取海外数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void resForeginData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter writer = response.getWriter();
		String httpUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_foreign";
		String res = requestUrl(httpUrl, "");
		res = StringEscapeUtils.unescapeJavaScript(res);
		int cnt = res.lastIndexOf("\"");
		StringBuffer result = new StringBuffer(res);
		result = result.deleteCharAt(16);
		result = result.deleteCharAt(cnt-1);
		writer.print(result);
	}
	
	
	
	
	/**
	 * 后台请求api
	 * @param httpUrl
	 * @param httpArg
	 * @return
	 */
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
