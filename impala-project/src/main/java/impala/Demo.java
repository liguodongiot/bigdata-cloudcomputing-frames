package impala;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
public class Demo {
    private static final String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
    private static final String CONNECTION_URL = "jdbc:impala://10.250.20.112:21050/dsst";
    protected final static Logger LOGGER  = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL);

            //InputStream inputStream = Demo.class.getResourceAsStream("/sql/show_table.sql");
            InputStream inputStream = Demo.class.getResourceAsStream("/sql/count.sql");
            String sql;
            try {
                sql = IOUtils.toString(inputStream,"UTF-8");
            } catch (IOException e) {
                System.out.println("IO error. "+e);
                return;
            }
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
