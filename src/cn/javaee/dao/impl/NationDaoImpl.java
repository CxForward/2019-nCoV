package cn.javaee.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.javaee.bean.Nation;
import cn.javaee.dao.NationDao;
import cn.javaee.utils.C3P0Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class NationDaoImpl implements NationDao{
	ComboPooledDataSource dateSource = C3P0Utils.getDataSource();
	Connection con = C3P0Utils.getConnection();
	QueryRunner queryRunner = new QueryRunner(dateSource);
	

	@Override
	public void insertByAll(String provinceName, int currentConfirmedCount,
			int confirmedCount, int curedCount, int deadCount,
			String continents, String date) {
		String sql = "insert into nation(provinceName,currentConfirmedCount,confirmedCount,"
				+ "curedCount,deadCount,continents,date) values(?,?,?,?,?,?,?)";
		Object[] params = { provinceName, 
				currentConfirmedCount, confirmedCount,
				curedCount, deadCount, continents, date };
		try {
			
			queryRunner.update(con, sql, params);
			
		} catch (SQLException e) {
			System.out.println("国外数据添加失败");
			// TODO Auto-generated catch block
			
		//	System.out.println("添加失败: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
	}

	@Override
	public List<Nation> selectByDate(String date) {
		String sql = "select provinceName,currentConfirmedCount,confirmedCount,date"
				+ " from nation where date = ?";
		List<Nation> list = null;
		ResultSetHandler<List<Nation>> rsh = new BeanListHandler<>(
				Nation.class);
		try {
			list = (List<Nation>) queryRunner.query(con, sql, rsh, date);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateByDate(String provinceName, int currentConfirmedCount,
			int confirmedCount, int curedCount, int deadCount,
			String continents, String date) {
		String sql = "update nation set provinceName=?,currentConfirmedCount=?,confirmedCount=?,curedCount=?,deadCount=?,continents=? where provinceName=? and date=?";
		Object[] params = { provinceName,
				currentConfirmedCount, confirmedCount,
				curedCount, deadCount, continents,provinceName, date };
		try {
			
			 queryRunner.update(con, sql, params);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
