package cn.javaee.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.javaee.bean.Province;
import cn.javaee.web.ProvinceWeb;
import cn.javaee.web.impl.ProvinceWebImpl;

public class BaseServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = req.getParameter("method");
		if (method == null || method.trim().isEmpty() || method.equals("")) {
			throw new RuntimeException("menthod参数不存在");
		}
		Class clazz = this.getClass();
		Method m = null;
		try {
			m = clazz.getMethod(method, HttpServletRequest.class,
					HttpServletResponse.class);
			if (m != null)
				m.invoke(this, req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*@Override
	public void init() throws ServletException {
		ProvinceWeb web = new ProvinceWebImpl();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now = df.format(new Date());
		List<Province> list = web.selectByDate(now);
		String[] dates = new String[]{};
		int i = 0;
		while (list.size() == 0) {
			System.out.println(i);
			dates[i] = now;
			i++;
			now = getLastDay(now);
			list = web.selectByDate(now);
		}
		if (dates.length > 0) {
			System.out.println("123");
			for (String string : dates) {
				web.insertByDate(string);
			}
		}
	}

	// 获取上一天的日期
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
		// 此处修改为+1则是获取后一天
		calendar.set(Calendar.DATE, day - 1);

		String lastDay = sdf.format(calendar.getTime());
		return lastDay;
	}*/
}
