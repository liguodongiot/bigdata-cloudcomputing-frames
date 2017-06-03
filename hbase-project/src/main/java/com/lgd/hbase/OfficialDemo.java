package com.lgd.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

import java.io.File;
import java.io.IOException;

/**
 * Describe:
 *
 * author: guodong.li
 * datetime: 2017/6/2 13:48
 */
public class OfficialDemo {

    private static final String TABLE_NAME = "table_demo2";
    private static final String CF_DEFAULT = "col_family_one2";

    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        //表存在，先删除
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            //表名
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            //列族
            table.addFamily(new HColumnDescriptor(CF_DEFAULT).setCompressionType(Algorithm.NONE));

            System.out.print("Creating table. ");
            createOrOverwrite(admin, table);
            System.out.println(" Done.");
        }
    }


    public static void modifySchema (Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            TableName tableName = TableName.valueOf(TABLE_NAME);
            if (!admin.tableExists(tableName)) {
                System.out.println("Table does not exist.");
                System.exit(-1);
            }

            HTableDescriptor table = admin.getTableDescriptor(tableName);

            //添加新的列族
            // Update existing table
            HColumnDescriptor newColumn = new HColumnDescriptor("NEWCF");
            newColumn.setCompactionCompressionType(Algorithm.GZ);
            newColumn.setMaxVersions(HConstants.ALL_VERSIONS);
            admin.addColumn(tableName, newColumn);

            //更新已存在的列族
            // Update existing column family
            HColumnDescriptor existingColumn = new HColumnDescriptor(CF_DEFAULT);
            existingColumn.setCompactionCompressionType(Algorithm.GZ);
            existingColumn.setMaxVersions(HConstants.ALL_VERSIONS);
            table.modifyFamily(existingColumn);
            admin.modifyTable(tableName, table);

            // Disable an existing table
            admin.disableTable(tableName);

            //删除一个列族
            // Delete an existing column family
            admin.deleteColumn(tableName, CF_DEFAULT.getBytes("UTF-8"));

            //删除表
            // Delete a table (Need to be disabled first)
            admin.deleteTable(tableName);
        }
    }


    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "D:\\install\\hadoop-2.6.0");
        Configuration config = HBaseConfiguration.create();
        //添加配置文件
        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
        config.addResource(new Path( "core-site.xml"));
        config.addResource(new Path( "hbase-site.xml"));

        createSchemaTables(config);
        //modifySchema(config);
    }

}
