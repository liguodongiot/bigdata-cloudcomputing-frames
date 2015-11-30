<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
    <body>
        <form action="${pageContext.request.contextPath}/emp/deleteAll.action" method="post">
            <table border="2" align="center">
                <tr>
                    <th>编号:</th>
                    <th>姓名:</th>
                </tr>

                <tr>
                    <td><input type="checkbox" name="ids" value="1" /></td>
                    <td>科比</td>
                </tr>

                <tr>
                    <td><input type="checkbox" name="ids" value="2" /></td>
                    <td>詹姆斯</td>
                </tr>

                <tr>
                    <td><input type="checkbox" name="ids" value="3" /></td>
                    <td>库里</td>
                </tr>

                <tr>
                    <td><input type="checkbox" name="ids" value="4" /></td>
                    <td>杜兰特</td>
                </tr>

                <tr>
                    <td><input type="checkbox" name="ids" value="5" /></td>
                    <td>戴维斯</td>
                </tr>

                <tr>
                    <td>
                        <input type="submit" value="删除" />
                    </td>

                </tr>

            </table>
        </form>
    </body>
</html>