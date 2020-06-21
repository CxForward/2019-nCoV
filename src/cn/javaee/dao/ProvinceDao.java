package cn.javaee.dao;

import java.util.List;

import cn.javaee.bean.Province;

public interface ProvinceDao {
	void insertByAll(String provinceName, String provinceShortName,
			int currentConfirmedCount, int confirmedCount, int suspectedCount,
			int curedCount, int deadCount, String comment, String date);

	List<Province> selectByDate(String date);
	void updateByDate(String provinceName, String provinceShortName,
			int currentConfirmedCount, int confirmedCount, int suspectedCount,
			int curedCount, int deadCount, String comment, String date);
}
