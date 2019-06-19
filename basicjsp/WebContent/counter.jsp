<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 위에다가 imports = "java.sql.*" 해서 import 처리할수도 O -->
<%!
	int cnt;
	int totalLen;
	
	public void init() {
		cnt = 0;
		totalLen = 8;
	}
%>
<%
	cnt++;
	String cntStr = cnt + ""; //137
	int cntLen = cntStr.length(); //3
	int zeroLen = totalLen - cntLen; //5
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	당신은 
<%
	for(int i=0;i<zeroLen;i++) {
%>
	<img src= "/basicservlet/img/0.png" width="50">
<%
	}
	for(int i=0;i<cntLen;i++) {
%>
	<img src="/basicjsp/img/<%= cntStr.charAt(i) %>.png" width="50">
<% 
	}
%>	
	 번째 방문자입니다.
</body>
</html>