package impala;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: PACKAGE_NAME</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/4/18 16:09 星期三
 */
public class DemoHiveTest {

    private static final String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
    private static final String CONNECTION_URL = "jdbc:hive2://10.250.322.43:21050/;auth=noSasl";
    protected final static Logger LOGGER  = LoggerFactory.getLogger(DemoHiveTest.class);

    public static void main(String[] args) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL);

            String sql="show databases;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }
        } catch (Exception e) {
            System.out.println("IO error. "+e);
        } finally {
            //关闭rs、ps和con
            try {
                if (ps != null){
                    ps.close();
                }
                if (rs != null){
                    rs.close();
                }
                if (con != null){
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("IO error. "+e);
            }
        }
    }

}
