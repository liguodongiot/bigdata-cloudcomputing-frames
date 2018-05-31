package impala;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;

public class ImpalaKerberosDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        test();
    }


    public static void test() throws IOException, ClassNotFoundException {
        Configuration conf = new Configuration();
        String impalaUrl ="jdbc:impala://10.32.32.189:21050/;AuthMech=1;KrbHostFQDN=gz-dsa-dev-app5-140-89.fds.host;KrbRealm=DSFDSSF.HOST;KrbServiceName=impala";
        conf.set("hadoop.security.authentication", "Kerberos");
        //设置系统的Kerberos配置信息
        System.setProperty("java.security.krb5.conf","D:/impala/krb5.conf");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab("finance","D:/impala/finance.keytab");
        UserGroupInformation loginUser = UserGroupInformation.getLoginUser();
        String query = "show databases;";
        String driverName = "com.cloudera.impala.jdbc41.Driver";
        Class.forName(driverName);
        loginUser.doAs((PrivilegedAction<Void>) () -> {
            try {

                try (Connection connection = DriverManager.getConnection(impalaUrl)) {
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            System.out.println(resultSet.getObject(1));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }





}
