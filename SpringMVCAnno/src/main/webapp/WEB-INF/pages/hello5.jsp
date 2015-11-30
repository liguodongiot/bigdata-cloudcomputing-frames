<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<h1>/WEB-INF/pages/hello5.jsp</h1>
<h1>成功</h1>
<h1>id: ${user.id}</h1>
<h1>username: ${user.username}</h1>
<h1>salary: ${user.salary}</h1>
<h1>hiredate: ${user.hiredate}</h1>
<h1>hiredate:
    <fmt:formatDate
        value="${user.hiredate}"
        type="both"
        dateStyle="full"
        timeStyle="default" />
</h1>
</body>
</html>