package cn.javaee.dao;

import java.util.List;

import cn.javaee.bean.Nation;

public interface NationDao {
	void insertByAll(String provinceName, int currentConfirmedCount,
			int confirmedCount, int curedCount, int deadCount,
			String continents, String date);

	List<Nation> selectByDate(String date);

	void updateByDate(String provinceName, int currentConfirmedCount,
			int confirmedCount, int curedCount, int deadCount,
			String continents, String date);
}
