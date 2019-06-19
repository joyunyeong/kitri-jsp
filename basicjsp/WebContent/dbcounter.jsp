<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<%!
int cnt;
int totalLen;

public void init() {
	cnt = 0;
	totalLen = 8;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}
%>
<%
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
		String sql = "";
		sql += "select no \n";
		sql += "from counter";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		rs.next();
		cnt = rs.getInt(1);
		
		sql = "update counter \n";
		sql += "set no = no + 1 \n";
		stmt.executeUpdate(sql);
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	String cntStr = cnt + "";//137
	int cntLen = cntStr.length();//3
	int zeroLen = totalLen - cntLen;//5
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align="center">
당신은<br>
<%
for(int i=0;i<zeroLen;i++) {
%>
<img src="/basicjsp/img/0.png" width="50">
<%
}
for(int i=0;i<cntLen;i++) {
%>
<img src="/basicjsp/img/<%= cntStr.charAt(i) %>.png" width="50">
<%
}
%>
<br>번째 방문자입니다.
</div>
</body>
</html>