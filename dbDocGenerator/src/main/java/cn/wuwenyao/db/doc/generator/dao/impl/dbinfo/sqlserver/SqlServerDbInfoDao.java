package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.sqlserver;

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

public final class SqlServerDbInfoDao extends AbstractDbInfoDao {


    private static final Logger logger = getLogger(SqlServerDbInfoDao.class);
    
   /* SELECT
    A.name ,C.value
    FROM sys.tables A

    LEFT JOIN sys.extended_properties C ON C.major_id = A.object_id
    WHERE C.minor_id=0 
    group by A.name ,C.value*/
    //private String sqlTables="SELECT A.relname AS TABLE_NAME,b.description AS table_comment,A.oid AS oid FROM pg_class A LEFT OUTER JOIN pg_description b ON b.objsubid = 0 AND A.oid = b.objoid left join pg_namespace c on A.relnamespace=c.oid WHERE c.nspname in(@schema) AND A.relkind = 'r' ORDER BY A.relname";
    private String sqlTables="SELECT cast(A.name as varchar(500)) AS TABLE_NAME,cast(C.value as varchar(500)) AS table_comment FROM sys.tables A LEFT JOIN sys.extended_properties C ON C.major_id = A.object_id WHERE C.minor_id=0 group by A.name ,C.value";
    
    /**
     * SELECT DB_NAME(dbid) FROM master.dbo.sysprocesses WHERE status='runnable';

		SELECT 'My Server is: ' + @@servername  AS DBServer
		SELECT 'My Current Database is: ' +  db_name()  AS CurrentDataBase


		select db_name() 
     */
    @Override
    public String databaseName() {
    	logger.info("select db_name()");
        //String databaseName = jdbcTemplate.queryForObject("SELECT DB_NAME(dbid) FROM master.dbo.sysprocesses WHERE status='runnable'", String.class);
        String databaseName = jdbcTemplate.queryForObject("select db_name()", String.class);
        return databaseName;
    }

    private List<TableInfo> queryTableInfo() {
    	/**
    	 * 原sql：SELECT t.name AS name,b.value AS comments
FROM sys.objects t LEFT JOIN sys.extended_properties b ON b.major_id=t.object_id and b.minor_id=0 and b.class=1 AND b.name='MS_Description'
WHERE t.type='U'
<if test="name != null and name != ''">
   AND t.name = upper(#{name})
</if>
ORDER BY t.name
修改后：SELECT  cast(t.name as varchar(500))  AS name,cast(b.value as varchar(500))  AS comments
FROM sys.objects t LEFT JOIN sys.extended_properties b ON b.major_id=t.object_id and b.minor_id=0 and b.class=1 AND b.name='MS_Description'
WHERE t.type='U'
<if test="name != null and name != ''">
   AND t.name = upper(#{name})
</if>
ORDER BY t.name
原因: 是由于SqlServer的ＪＤＢＣ的缺陷, 需要强制转换.

PS：可能由于用惯了mysql，遇到这种恶心人的玩意我就想骂娘。
————————————————
版权声明：本文为CSDN博主「乄星落」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_37612755/article/details/88735286
    	 */
    	//logger.info("schema=="+applicationConfig.getGenerator().getSchema());
    	//String sql = sqlTables.replace("@schema",applicationConfig.getGenerator().getSchema().replaceAll("\\\\", "'"));
    	//logger.info("sqlTables:"+sql);
    	String sql=sqlTables;
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
            executor.execute(new GetSqlServerTableInfoTask(tableInfo, countDownLatch, jdbcTemplate));
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
