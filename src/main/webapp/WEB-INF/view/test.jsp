<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/4/15
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<shiro:user>
    <shiro:principal/>
</shiro:user>

<shiro:guest>
    还么有登陆
</shiro:guest>
</body>
</html>
