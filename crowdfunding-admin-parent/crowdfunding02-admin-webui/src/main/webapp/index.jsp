<%--
  Created by IntelliJ IDEA.
  User: Hypocritical Fish
  Date: 2022/4/26
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合环境</a>
<br/>
<button id="btn1">Send [5,8,12] One</button>
<br/>
<button id="btn2">Send [5,8,12] two</button>
<br/>
<button id="btn3">Send Object</button>
<br/>
<button id="btn4">layer</button>
<br/>
</body>
</html>
