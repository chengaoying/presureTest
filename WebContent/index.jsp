<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
	
%>

<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>存档压力测试</title>
</head>

<script type="text/javascript">
	function check() {
		alert(111);
		var url = document.getElementsByName("url");
		var threadcount = document.getElementsByName("threadcount");
		
		if(url == null){
			alert("服务器地址不能空!");
		}
		if(threadcount == null){
			alert("在线人数不能空!");
		}
	} 

</script>

<body>

<form action="<%=basePath%>record" method="post">

	服务器地址:<input type="text" name="url"/><br/><!--
	端口:<input type="text" name="port" /><br/>-->
	在线人数:<input type="text" name="threadcount" /><br/>
	<input type="submit" value="测试"/>
</form>

</body>
</html>