package cn.enilu.tool.database.doc.generator;

import cn.enilu.tool.database.doc.generator.database.*;
import org.nutz.dao.impl.SimpleDataSource;

import java.util.Scanner;

/**
 * Main
 *
 * @author zt
 * @version 2018/10/6 0006
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("choose database:\n1:mysql\n2:oracle\n3:PostgreSQL\n4:SqlServer\n" +
                "Select the appropriate numbers choose database type\n" +
                "(Enter 'c' to cancel): ");
        String dbType = sc.nextLine();
        if("c".equals(dbType)){
            System.exit(-1);
        }
        if(Integer.valueOf(dbType)<1 ||Integer.valueOf(dbType)>4){
            System.out.println("wrong number,will exit");
            System.exit(-1);
        }
        String serviceName =null;
        if("2".equals(dbType)){
            System.out.println("input service name:");
            serviceName = sc.nextLine();
        }
        String dbName = null;
        if("1".equals(dbType) || "3".equals(dbType)){
            System.out.println("input database name:");
            dbName = sc.nextLine();
        }
        System.out.println("input host:");
        String ip = sc.nextLine();
        System.out.println("input port:");
        String port = sc.nextLine();


        System.out.println("input username:");
        //String username = sc.nextLine();
        String username = "sa";

        System.out.println("input password:");
        //String passowrd = sc.nextLine();
        String passowrd = "zrt123456";

        SimpleDataSource dataSource = new SimpleDataSource();
        if("1".equals(dbType)) {
            dataSource.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + dbName);
        }else if("2".equals(dbType)){
            dataSource.setJdbcUrl("jdbc:oracle:thin:@"+ip+":"+port+":"+serviceName);
        }else if("3".equals(dbType)){
            dataSource.setJdbcUrl("jdbc:postgresql://"+ip+":"+port+"/"+dbName);
        }else if("4".equals(dbType)){
            dataSource.setJdbcUrl("jdbc:sqlserver://"+ip+":"+port+";database="+"sq_9219");
        }
        dataSource.setUsername(username);
        dataSource.setPassword(passowrd);
        Generator generator = null;
        switch (dbType){
            case "1":
                 generator = new MySQL(dbName,dataSource);
                break;
            case "2":
                generator = new Oracle(username,dataSource);
                break;
            case "3":
                generator = new PostgreSQL(dbName,dataSource);
                break;
            case "4":
                generator = new SqlServer(dbName,dataSource);
        }

        generator.generateDoc();
    }
}
