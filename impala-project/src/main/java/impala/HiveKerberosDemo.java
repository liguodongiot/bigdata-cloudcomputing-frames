package impala;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.*;

public class HiveKerberosDemo {

    public static void main(String[] args) {

        //System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.5");

        String driverName = "org.apache.hive.jdbc.HiveDriver";

        String url ="jdbc:hive2://10.32.32.89:21050/ai_data;principal=impala/gz-public-dev-app5-140-89.ddd.host@DDDSA.HOST";

        //设置系统的Kerberos配置信息
        System.setProperty("java.security.krb5.conf","D:/impala/krb5.conf");

        Configuration conf = new Configuration();
        //设置认证方式为Kerberos
        conf.set("hadoop.security.authentication", "Kerberos");
        try {
            UserGroupInformation.setConfiguration(conf);
            //认证的用户和对应的keytab文件
            UserGroupInformation.loginUserFromKeytab("finance","D:/impala/finance.keytab");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(driverName);

            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();
            //String sql = "show databases";
            String sql = "show tables;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            try {
//                conn.close();
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
    }






}
