package com.kitri.util;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
//	
//	static {
//		try {
//			Class.forName(SiteConstance.DB_DRIVER);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static Connection makeConnection() throws SQLException {
//		return DriverManager.getConnection(SiteConstance.DB_URL, SiteConstance.DB_USERNAME, SiteConstance.DB_PASWORD);
//	}
	
	public static Connection makeConnection() throws SQLException {
		try {
			Context ictx = new InitialContext(); // 윈도우 탐색기 염!
			Context ctx = (Context) ictx.lookup("java:comp/env");
			DataSource dataSource = (DataSource) ctx.lookup("jdbc/kitri"); // kitri라는 이름을 찾아라
			
			return dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
