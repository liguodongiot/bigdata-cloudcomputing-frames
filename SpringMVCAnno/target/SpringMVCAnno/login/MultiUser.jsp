<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<body>
    普通用户
    <form action="${pageContext.request.contextPath}/person/register.action" method="post">
        <table border="2" align="center">
            <caption><h2>批量注册员工</h2></caption>
            <tr>
                <th>姓名:</th>
                <td><input type="text" name="user.username" value="${user.username}"/></td>
            </tr>
            <tr>
                <th>月薪:</th>
                <td><input type="text"
                           name="user.salary"
                           value="${user.salary}" /></td>
            </tr>

            <tr>
                <th>入职时间:</th>
                <td><input type="text"
                           name="user.hiredate"
                           value='<fmt:formatDate
                                    value="${user.hiredate}"
                                    type="date"
                                    dateStyle="default"
                                    />'
                        />
                </td>
            </tr>


            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="普通用户注册" style="width:111px"/>
                </td>
            </tr>

        </table>
    </form>

    <hr />

    管理员
    <form action="${pageContext.request.contextPath}/person/register.action" method="post">
        <table border="2" align="center">
            <tr>
                <th>姓名:</th>
                <td><input type="text" name="admin.username" value="${admin.username}"/></td>
            </tr>

            <tr>
                <th>月薪:</th>
                <td><input type="text"
                           name="admin.salary"
                           value="${admin.salary}" /></td>
            </tr>

            <tr>
                <th>入职时间:</th>
                <td><input type="text"
                           name="admin.hiredate"
                           value='<fmt:formatDate
                                    value="${admin.hiredate}"
                                    type="date"
                                    dateStyle="default"
                                    />'
                        />
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="管理员注册" style="width:111px"/>
                </td>
            </tr>

        </table>
    </form>

</body>
</html>