<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
    <body>
        <form action="${pageContext.request.contextPath}/emp/addAll.action" method="post">
            <table border="2" align="center">

                <tr>
                    <td><input type="text" name="empList[0].username" value="乔丹" /></td>
                    <td><input type="text" name="empList[0].salary" value="7000" /></td>
                </tr>

                <tr>
                    <td><input type="text" name="empList[1].username" value="梅西" /></td>
                    <td><input type="text" name="empList[1].salary" value="8000" /></td>
                </tr>

                <tr>
                    <td><input type="text" name="empList[2].username" value="姚明" /></td>
                    <td><input type="text" name="empList[2].salary" value="10000" /></td>
                </tr>

                <tr>
                    <td><input type="text" name="empList[3].username" value="丁俊晖" /></td>
                    <td><input type="text" name="empList[3].salary" value="10000" /></td>
                </tr>

                <tr>
                    <td><input type="text" name="empList[4].username" value="周琦" /></td>
                    <td><input type="text" name="empList[4].salary" value="10000" /></td>
                </tr>

                <tr>
                    <td>
                        <input type="submit" value="批量注册" />
                    </td>

                </tr>

            </table>
        </form>
    </body>
</html>