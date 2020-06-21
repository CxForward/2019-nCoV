package cn.javaee.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.javaee.bean.Province;

public interface ProvinceWeb {
	boolean insertByDate(String date);

	List<Province> selectByDate(String date);
	void updateByDate(String date);
	JSONObject requestUrl(String httpUrl, String httpArg);
}
