package org.sqlToDoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * Description: ���ݿ⹤����
 * 
 * @author jiujiya
 * @version 1.0 
 */
public class DBHelper {
	
	/** ���ݿ�url */
	public static String url;
	/** ���ݿ������� */
	public static String driverName;
	/** ���ݿ��û��� */
	public static String user;
	/** ���ݿ����� */
	public static String password;
	/** ���ݿ����� */
	public static String dbname;
	
	public Connection conn = null;
	
	public PreparedStatement pst = null;

	public DBHelper(String sql) {
		try {
			Class.forName(driverName);
			this.conn = DriverManager.getConnection(url, user, password);
			this.pst = this.conn.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("���ݿ�����ʧ�ܣ�" + e.getMessage());
		}
	}
	
	/**
	 * @return ��ȡ��Ŀ���еı���ֶ�
	 */
	public static List<Map<String, Object>> getTableAndColumns() {
		String sql1 = "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + DBHelper.dbname + "'";
		List<Map<String, Object>> tables = DBHelper.queryForList(sql1);
		for (Map<String, Object> table : tables) {
			// ��ѯ��ṹ
			String sql = "select COLUMN_NAME, COLUMN_TYPE, COLUMN_KEY, COLUMN_COMMENT, IS_NULLABLE from information_schema.columns where table_schema = '"
					+ DBHelper.dbname + "' and table_name = '" + table.get("TABLE_NAME") + "'";
			List<Map<String, Object>> columns = DBHelper.queryForList(sql);
			for (Map<String, Object> map : columns) {
				map.put("IS_NULLABLE", "NO".equals(map.get("IS_NULLABLE")) ? "��" : "��");
				map.put("COLUMN_KEY", "PRI".equals(map.get("COLUMN_KEY")) ? "��" : "��");
			}
			table.put("columns", columns);
		}
		return tables;
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * queryForList
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> queryForList(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			DBHelper db1 = new DBHelper(sql);
			ResultSet ret = db1.pst.executeQuery();
			ResultSetMetaData rsmd = ret.getMetaData();
			int columnCount = ret.getMetaData().getColumnCount();
			while (ret.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i < columnCount + 1; ++i)
					map.put(rsmd.getColumnName(i), ret.getObject(i));

				list.add(map);
			}
			ret.close();
			db1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("���ݿ��ѯʧ�ܣ�" + e.getMessage());
		}
		return list;
	}
}