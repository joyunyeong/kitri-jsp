package com.kitri.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class MemberLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String name = null;
		
//		1. data get (아이디, 비번)
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
//		2. Logic : DB에 있는 데이터(ID를 토대로) 이름 Select
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT name\n");
			sql.append("FROM member\n");
			sql.append("WHERE id = ? and pass = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 역순으로 닫아라
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//	3. response page 만들기
//		3-1 : name != null : 홍길동님 안녕하세요.
//		3-2 : name = null : 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못입력하셨습니다.
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();	
		out.println("<html>");
		out.println("	<body>");
		if(name != null) {
			out.println("<strong>" + name + "</strong>님 안녕하세요<br>");
		} else {
			out.println("<font color = \"red\">");
			out.println("등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못입력하셨습니다.");
			out.println("</font>");
			out.println("<a href=\"/memberservlet/user/login.html\">로그인</a>");
	}}}


