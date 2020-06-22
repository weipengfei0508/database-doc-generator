package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.sqlserver;

import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql.TableFieldInfoRowMapper;
import cn.wuwenyao.db.doc.generator.entity.TableFieldInfo;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import cn.wuwenyao.db.doc.generator.entity.TableKeyInfo;
import org.slf4j.Logger;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.slf4j.LoggerFactory.getLogger;

/***
 * 任务-获取表信息
 * @author wenyao.wu
 * @date 2019/1/30
 */
public class GetSqlServerTableInfoTask implements Runnable {

    private static final Logger logger = getLogger(GetSqlServerTableInfoTask.class);

    private TableInfo tableInfo;

    private CountDownLatch countDownLatch;

    private JdbcTemplate jdbcTemplate;
    
    
   /* private String sqlColumns = "SELECT a.attname AS COLUMN_NAME,'' as COLUMN_DEFAULT,'' as COLUMN_KEY,'' as EXTRA,t.typname AS COLUMN_TYPE,a.attlen AS length,a.atttypmod AS lengthvar,a.attnotnull AS IS_NULLABLE,b.description AS COLUMN_COMMENT" +
            " FROM pg_class c,pg_attribute a LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,pg_type t" +
            " WHERE c.relname = ? and a.attnum > 0 and a.attrelid = c.oid and a.atttypid = t.oid" +
            " ORDER BY a.attnum";*/
    /**
     * SELECT
A.name AS table_name,
B.name AS column_name,
C.value AS column_description
FROM sys.tables A
INNER JOIN sys.columns B ON B.object_id = A.object_id
LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id 
WHERE A.name = 'TABLE'
     */
    private String sqlColumns ="SELECT cast(B.name as varchar(500)) AS COLUMN_NAME,'' as COLUMN_DEFAULT,'' as COLUMN_KEY,'' as EXTRA,'' AS COLUMN_TYPE,0 AS length,'' AS lengthvar,'' AS IS_NULLABLE,cast(C.value as varchar(500))  AS COLUMN_COMMENT FROM sys.tables A "+
         " INNER JOIN sys.columns B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = ? ";

    
//    private String sqlprikey="SELECT pg_class.relname AS tab_name,pg_constraint.conname AS Key_name,pg_attribute.attname AS Column_name,pg_type.typname AS typename FROM (((pg_constraint JOIN pg_class ON ((pg_constraint.conrelid = pg_class.oid))) JOIN pg_attribute ON (((pg_attribute.attrelid = pg_class.oid) AND (pg_attribute.attnum = pg_constraint.conkey[1])))) JOIN pg_type ON ((pg_type.oid = pg_attribute.atttypid))) "+
//    						" WHERE (pg_constraint.contype = 'p'::\"char\") and pg_class.relname='@tablename'";

     private String sqlprikey="select a.table_name as tab_name,a.CONSTRAINT_NAME as Key_name,b.column_name,'' as typename "+
     " from information_schema.table_constraints a inner join information_schema.constraint_column_usage b on a.constraint_name = b.constraint_name"+
    " where a.constraint_type = 'PRIMARY KEY' and a.table_name = ? ";

     
     
     
     
      
    
    public GetSqlServerTableInfoTask(TableInfo tableInfo, CountDownLatch countDownLatch, JdbcTemplate jdbcTemplate) {
        this.tableInfo = tableInfo;
        this.countDownLatch = countDownLatch;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void run() {
        try {
            tableInfo.setFields(queryFields());
            tableInfo.setKeys(queryKeys());
            logger.info("表：{}信息查询完毕", tableInfo.getTableName());
        } catch (Exception e) {
            logger.error("任务-获取表信息-异常", e);
        } finally {
            countDownLatch.countDown();
        }

    }

    /***
     * 查询 fields
     * @return
     */
    private List<TableFieldInfo> queryFields() {
    	logger.info("sqlColumns="+sqlColumns);
        Object[] params = new Object[]{tableInfo.getTableName()};
        List<TableFieldInfo> fields = jdbcTemplate.query(
        		sqlColumns,
                params, new TableFieldInfoRowMapper());
        return fields;
    }

    /***
     * 查询 索引
     * @return
     */
    private List<TableKeyInfo> queryKeys() {
    	//String sql = sqlprikey.replace("@tablename",tableInfo.getTableName());
    	logger.info("sqlprikey="+sqlprikey);
    	Object[] params = new Object[]{tableInfo.getTableName()};
        List<Map<String, Object>> rawKeyInfos = jdbcTemplate.query(sqlprikey, params,
                new ColumnMapRowMapper());

        Map<String, TableKeyInfo> keyMap = new HashMap<>(5);
        for (Map<String, Object> rawKeyInfo : rawKeyInfos) {
            TableKeyInfo tableKeyInfo = keyMap.get(rawKeyInfo.get("Key_name").toString());
            String columnName = rawKeyInfo.get("Column_name").toString();
            if (tableKeyInfo == null) {
                tableKeyInfo = new TableKeyInfo();
                ArrayList<String> columns = new ArrayList<>();
                columns.add(columnName);
                tableKeyInfo.setColumns(columns);
                tableKeyInfo.setIndexComment(rawKeyInfo.get("Index_comment")==null ? "" : rawKeyInfo.get("Index_comment").toString());
                tableKeyInfo.setIndexType("");
                tableKeyInfo.setName(rawKeyInfo.get("Key_name").toString());
                tableKeyInfo.setUnique(true);
            } else {
                tableKeyInfo.getColumns().add(columnName);
            }
            keyMap.put(rawKeyInfo.get("Key_name").toString(), tableKeyInfo);
        }
        //索引信息进行排序
        List<TableKeyInfo> tableKeyInfoList = new ArrayList<>(keyMap.values());
        tableKeyInfoList.sort(null);
        return tableKeyInfoList;
    	
    }

}