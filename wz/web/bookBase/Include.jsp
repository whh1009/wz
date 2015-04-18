<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            $("#__menuNav").html("<%=request.getSession().getAttribute("menuNav")%>");
        })
        
    </script>
</head>
<body>
    <div id="__menuNav"></div>
</body>
</html>
