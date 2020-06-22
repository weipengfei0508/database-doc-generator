package cn.wuwenyao.db.doc.generator.enums;


import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql.MysqlDbInfoDao;

import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.pg.PgDbInfoDao;
import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.sqlserver.SqlServerDbInfoDao;

/***
 * 数据库类型
 * 
 * @author wwy shiqiyue.github.com
 *
 */
public enum DbType {
	
	/** mysql */
	MYSQL(MysqlDbInfoDao.class),
	/**SQLSERVER*/
	SqlServerSQL(SqlServerDbInfoDao.class),
	/** postgresql */
	PostgreSQL(PgDbInfoDao.class);
	
	
	
	private Class dbInfoDaoImpl;
	
	private DbType(Class dbInfoDaoImpl) {
		this.dbInfoDaoImpl = dbInfoDaoImpl;
	}
	
	public Class getDbInfoDaoImpl() {
		return dbInfoDaoImpl;
	}
	
}
