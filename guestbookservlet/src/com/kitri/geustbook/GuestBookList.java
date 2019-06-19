package com.kitri.geustbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gblist")
public class GuestBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
//		1. data get (이름, 아이디, 비번, 이메일1, 이메일2, 전번1, 전번2, 전번3, 우편번호, 주소, 상세주소)
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String emailid = request.getParameter("emailid");
		String emaildomain = request.getParameter("emaildomain");
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");
		String addressdetail = request.getParameter("address_detail");
		
//		2. Logic : 1의 data를 DB에 insert
		
//		insert all
//		into member (id, name, pass, emailid, emaildomain, joindate)
//		values (?, ?, ?, ?, ?, sysdate)
//		into member_detail (id, zipcode, address, address_detail, tel1, tel2, tel3)
//		values (?, ?, ?, ?, ?, ?, ?)
//		select * from dual
		
		int cnt = 0;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
			StringBuffer sql = new StringBuffer();
			sql.append("insert all\n");
			sql.append("	into member (id, name, pass, emailid, emaildomain, joindate)\n");
			sql.append("	values (?, ?, ?, ?, ?, sysdate)\n");
			sql.append("	into member_detail (id, zipcode, address, address_detail, tel1, tel2, tel3)\n");
			sql.append("	values (?, ?, ?, ?, ?, ?, ?)\n");
			sql.append("select * from dual\n");
			
			//현재 sql은 stringBuffer니까 toString으로 string으로 바꿔주기
			
			pstmt = conn.prepareStatement(sql.toString()); // 미리 sql 선언 : 이 부분에서 sql을 가져가야 하니까
			
			int idx = 0;
			pstmt.setString(++idx, id);
			pstmt.setString(++idx, name);
			pstmt.setString(++idx, pass);
			pstmt.setString(++idx, emailid);
			pstmt.setString(++idx, emaildomain);
			
			pstmt.setString(++idx, id);
			pstmt.setString(++idx, zipcode);
			pstmt.setString(++idx, address);
			pstmt.setString(++idx, addressdetail);
			pstmt.setString(++idx, tel1);
			pstmt.setString(++idx, tel2);
			pstmt.setString(++idx, tel3); // 여기까지 쿼리문을 실행할 값들 입력
			cnt = pstmt.executeUpdate(); //여기서 sql문장 가져갈 필요 X !!!
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 역순으로 닫아라
				if(pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//		3. reponse page 만들기 > 2의 결과에 따라
//		 3-1. !0 : 홍길동님 회원가입을 환영합니다.
//		 3-2. 0 : 서버문제로 회원가입이 실패하였습니다. 
//		                   다음에 다시 시도하세요.

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();	
		out.println("<html>");
		out.println("	<body>");
		if(cnt != 0) {
			out.println(name + "님 회원가입을 환영합니다.<br>");
			out.println("로그인 후 모든 서비스를 이용할 수 있습니다.");
			out.println("<a href=\"/memberservlet/user/login.html\">로그인</a>");
		} else {
			out.println("<font color = \"red\">");
			out.println("서버문제로 회원가입이 실패하였습니다.");
			out.println("다음에 다시 시도하세요.");
		}
	}
}
