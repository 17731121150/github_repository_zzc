<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/8
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../js/calendarTime.js"></script>
</head>
<body>
        <table>
            <tr>
                <td colspan="10">
                    按照用户名:<input type="text" name="momohu"><br>
                    按照年龄:<input type="text" name="sbirthday" onclick="setDay(this)">--至--
                    <input type="text" name="ebirthday" onclick="setDay(this)">
                </td>
            </tr>
            <tr>
                <th>编码</th>
                <th>姓名</th>
                <th>密码</th>
                <th>年龄</th>
                <th>生日</th>
                <th>部门</th>
                <th>角色</th>
                <th>操作</th>
            </tr>
            <c:forEach var="u" items="${list}">
                <tr>
                    <td>
                        ${u.id}
                    </td>
                    <td>
                            ${u.username}
                    </td>
                    <td>
                            ${u.password}
                    </td>
                    <td>
                            ${u.age}
                    </td>
                    <td>
                        <fmt:formatDate value="${u.birthday}" pattern="yyyy-MM-dd"></fmt:formatDate>
                    </td>
                    <td>
                            ${u.deptBean.dname}
                    </td>
                    <td>
                            ${u.roleBean.rname}
                    </td>
                    <td>
                            <a href="toUserDeptRole.do?id=${u.id}"><button>给员工分配部门和角色</button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
</body>
</html>
