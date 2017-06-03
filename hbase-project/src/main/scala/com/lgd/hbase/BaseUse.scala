package com.lgd.hbase

import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, HTable, Put}
import org.apache.hadoop.hbase.util.Bytes
/**
  * Describe:
  *     This example lists HBase tables, creates a new table, and adds a row to it.
  * author: guodong.li
  * datetime: 2017/6/3 13:43
  */
object BaseUse {

  def main(args: Array[String]): Unit = {

    val conf = HBaseConfiguration.create()
    conf.addResource(new Path("core-site.xml"))
    conf.addResource(new Path("hbase-site.xml"))

    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin

    // list the tables
    val listtables = admin.listTables
    listtables.foreach(println)

    // let's insert some data in 'mytable' and get the row
    val table = new HTable(conf, "table_demo")
    val theput = new Put(Bytes.toBytes("rowkey_one"))

    theput.add(Bytes.toBytes("col_family_one"), Bytes.toBytes("col_id"), Bytes.toBytes("col_value"))
    table.put(theput)

    val theget = new Get(Bytes.toBytes("rowkey_one"))
    val result = table.get(theget)
    val value = result.value
    println(Bytes.toString(value))
  }


}
