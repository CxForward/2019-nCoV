package cn.javaee.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import cn.javaee.bean.Province;
import cn.javaee.dao.ProvinceDao;
import cn.javaee.utils.C3P0Utils;

public class ProvinceDaoImpl implements ProvinceDao {
	ComboPooledDataSource dateSource = C3P0Utils.getDataSource();
	Connection con = C3P0Utils.getConnection();
	QueryRunner queryRunner = new QueryRunner(dateSource);
	

	@Override
	public void insertByAll(String provinceName, String provinceShortName,
			int currentConfirmedCount, int confirmedCount, int suspectedCount,
			int curedCount, int deadCount, String comment, String date) {
		String sql = "insert into province(provinceName,provinceShortName,currentConfirmedCount,confirmedCount,suspectedCount,"
				+ "curedCount,deadCount,comment,date) values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { provinceName, provinceShortName,
				currentConfirmedCount, confirmedCount, suspectedCount,
				curedCount, deadCount, comment, date };
		try {
			queryRunner.update(con, sql, params);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		//	System.out.println("添加失败: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
	}

	@Override
	public List<Province> selectByDate(String date) {
		String sql = "select provinceShortName,currentConfirmedCount,confirmedCount,date"
				+ " from province where date = ? limit 34";
		List<Province> list = null;
		ResultSetHandler<List<Province>> rsh = new BeanListHandler<>(
				Province.class);
		try {
			list = (List<Province>) queryRunner.query(con, sql, rsh, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateByDate(String provinceName, String provinceShortName,
			int currentConfirmedCount, int confirmedCount, int suspectedCount,
			int curedCount, int deadCount, String comment, String date) {
		String sql = "update province set provinceName=?,provinceShortName=?,currentConfirmedCount=?,confirmedCount=?,suspectedCount=?,curedCount=?,deadCount=?,comment=? where provinceName=? and date=?";
		Object[] params = { provinceName, provinceShortName,
				currentConfirmedCount, confirmedCount, suspectedCount,
				curedCount, deadCount, comment,provinceName, date };
		try {
			
			 queryRunner.update(con, sql, params);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
