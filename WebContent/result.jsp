<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
  int threadcount = Integer.parseInt(String.valueOf(request.getAttribute("threadcount")));
  int count = Integer.parseInt(String.valueOf(request.getAttribute("count")));
  long  lastSeconds = Long.parseLong(String.valueOf(request.getAttribute("lastSeconds")));

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

同时发送请求数:<%=threadcount %>次<br>
成功:<%=count %>次<br>
失败:<%=threadcount-count %>次<br>
耗时:<%=lastSeconds %>秒<br>

</body>
</html>