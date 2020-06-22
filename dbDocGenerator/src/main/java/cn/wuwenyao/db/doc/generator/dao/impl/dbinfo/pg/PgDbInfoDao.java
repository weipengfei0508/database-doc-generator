package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.pg;

import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.AbstractDbInfoDao;
import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql.TableInfoRowMapper;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/***
 * 获取pg数据库信息
 *
 * @author wwy
 *
 */

public final class PgDbInfoDao extends AbstractDbInfoDao {


    private static final Logger logger = getLogger(PgDbInfoDao.class);
    
    String namespace="log";
    
//    private String sqlTables = "SELECT  A .relname AS table_name, b.description AS table_comment,A .oid as oid" +
//            " FROM pg_class A LEFT OUTER JOIN pg_description b ON b.objsubid = 0 AND A .oid = b.objoid " +
//            "WHERE A .relnamespace = ( SELECT oid FROM pg_namespace WHERE nspname = '@schema' ) AND A .relkind = 'r' ORDER BY A .relname";

    private String sqlTables="SELECT A.relname AS TABLE_NAME,b.description AS table_comment,A.oid AS oid FROM pg_class A LEFT OUTER JOIN pg_description b ON b.objsubid = 0 AND A.oid = b.objoid left join pg_namespace c on A.relnamespace=c.oid WHERE c.nspname in(@schema) AND A.relkind = 'r' ORDER BY A.relname";
    
    @Override
    public String databaseName() {
    	logger.info("select current_database()");
        String databaseName = jdbcTemplate.queryForObject("select current_database()", String.class);
        return databaseName;
    }

    private List<TableInfo> queryTableInfo() {
    	logger.info("schema=="+applicationConfig.getGenerator().getSchema());
    	String sql = sqlTables.replace("@schema",applicationConfig.getGenerator().getSchema().replaceAll("\\\\", "'"));
    	logger.info("sqlTables:"+sql);
        List<TableInfo> tableInfos = jdbcTemplate.query(
        		sql,
                new TableInfoRowMapper());
        return tableInfos;
    }

    @Override
    public List<TableInfo> tableInfoList() {
        //查询表信息
        List<TableInfo> tableInfos = queryTableInfo();
        if (CollectionUtils.isEmpty(tableInfos)) {
            return tableInfos;
        }
        //过滤黑名单
        tableInfos = tableInfos.stream().filter(tableInfo -> {
            return isTableGenerate(tableInfo.getTableName());
        }).collect(Collectors.toList());
        //查询表-列信息
        CountDownLatch countDownLatch = new CountDownLatch(tableInfos.size());
        ExecutorService executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(tableInfos.size()));
        tableInfos.stream().forEach((tableInfo) -> {
            executor.execute(new GetPgTableInfoTask(tableInfo, countDownLatch, jdbcTemplate));
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("countDownLatch error", e);
        }
        executor.shutdown();
        return tableInfos;
    }
    
    public static void main(String[] args) {
		String test="\\log\\,\\base\\";
		String test1=test.replaceAll("\\\\", "'");
		System.out.println(test1);
	}

}
