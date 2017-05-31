package com.lgd.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    @Test
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

    @Test
    public void testFormMysqlToEs() {
        String index = "xma_faq_dev";
        String type = "correct_answer_info";
        List<CorrentAnswerInfo> list = new ArrayList<CorrentAnswerInfo>(10);

        try {

            String sql = "select \n" +
                    "id,\n" +
                    "ask,\n" +
                    "answer,\n" +
                    "pure_text_answer,\n" +
                    "classify_name,\n" +
                    "state,\n" +
                    "ask_type,\n" +
                    "match_mode,\n" +
                    "valid_term,\n" +
                    "update_time\n" +
                    "from \n" +
                    "correct_answer_info \n" +
                    "where id>=? \n" +
                    "limit 10";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            rs = ps.executeQuery();
            while(rs.next()){
                CorrentAnswerInfo correntAnswerInfo = new CorrentAnswerInfo();
                correntAnswerInfo.setId(rs.getInt(1));
                correntAnswerInfo.setAsk(rs.getString(2));
                correntAnswerInfo.setAnswer(rs.getString(3));
                correntAnswerInfo.setPureTextAnswer(rs.getString(4));
                correntAnswerInfo.setClassifyName(rs.getString(5));
                correntAnswerInfo.setState(rs.getString(6));
                correntAnswerInfo.setAskType(rs.getString("ask_type"));
                correntAnswerInfo.setMatchMode(rs.getString("match_mode"));
                correntAnswerInfo.setValidTerm(rs.getString("valid_term"));
                correntAnswerInfo.setUpdateTime(DateUtils.timestamp2Str(rs.getTimestamp("update_time")));
                System.out.println(correntAnswerInfo.toString());
                list.add(correntAnswerInfo);
            }

            for (CorrentAnswerInfo correntAnswer : list) {
                ObjectMapper objectMapper = new ObjectMapper();
                IndexResponse response = client.prepareIndex(index, type, String.valueOf(correntAnswer.getId()))
                        .setSource(objectMapper.writeValueAsString(correntAnswer))
                        .execute().actionGet();
                System.out.println(response.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
