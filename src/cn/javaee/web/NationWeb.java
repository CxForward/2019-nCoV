package cn.javaee.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.javaee.bean.Nation;

public interface NationWeb {
	boolean insertByDate(String date);

	List<Nation> selectByDate(String date);
	void updateByDate(String date);
	JSONObject requestUrl(String httpUrl, String httpArg);
}
