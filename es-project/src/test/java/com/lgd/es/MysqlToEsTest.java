package com.lgd.es;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/5/22 17:28
 */
public class MysqlToEsTest {

    TransportClient client = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Before
    public void before() throws Exception {
        client = EsUtils.init();
        conn = DbUtils.getMysqlConn();

    }
    @After
    public void after() {
        DbUtils.close(rs, ps, conn);
    }

    @Test
    public void testInsertMysql() {
        try {

            ps = conn.prepareStatement("insert into user_info(user_name) values(?)");
            ps.setString(1, "中国");
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testSelectMysql() {
        try {

            String sql = "select * from correct_answer_info where id>=? limit 5";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);//把大于2的记录都取出来
            rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(1)+"---"+rs.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
