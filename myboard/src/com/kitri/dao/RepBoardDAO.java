package com.kitri.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kitri.dto.RepBoard;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class RepBoardDAO {
	public void insert(RepBoard repBoard) {
		String insertSQL = 
	"insert into repboard("
	+"BOARD_SEQ, PARENT_SEQ, BOARD_SUBJECT, BOARD_WRITER,BOARD_CONTENTS,  BOARD_DATE,BOARD_PASSWORD,BOARD_VIEWCOUNT)"
	+" values(BOARD_SEQ.NEXTVAL, ?,      ?,            ?,             ?,systimestamp,             ?,           0)";
		Connection con = null;
		PreparedStatement pstmt =null;
		try {
			//1)JDBC드라이버로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)DB연결
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String user = "kitri";
			String password = "kitri";
			con = DriverManager.getConnection(url, user, password);

			pstmt = con.prepareStatement(insertSQL);
			pstmt.setInt(1, repBoard.getParent_seq());
			pstmt.setString(2, repBoard.getBoard_subject());
			pstmt.setString(3, repBoard.getBoard_writer());
			pstmt.setString(4, repBoard.getBoard_contents());
			pstmt.setString(5, repBoard.getBoard_password());			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<RepBoard> selectByRows(int startRow, int endRow) {
		List<RepBoard> list = new ArrayList<>();
		String selectByRowsSQL = "SELECT *\r\n" + 
				"FROM (SELECT rownum r, repboard.*\r\n" + 
				"      FROM repboard\r\n" + 
				"      START WITH parent_seq=0\r\n" + 
				"      CONNECT BY PRIOR board_seq = parent_seq\r\n" + 
				"      ORDER SIBLINGS BY board_seq DESC)\r\n" + 
				"WHERE r BETWEEN ? AND ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String user = "kitri";
			String password = "kitri";
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement(selectByRowsSQL);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 검색결과의 한행 정보를 RepBoard객체에 대입
				RepBoard r = new RepBoard();
				r.setBoard_seq(rs.getInt("board_seq"));
				r.setParent_seq(rs.getInt("parent_seq"));
				r.setBoard_subject(rs.getString("board_subject"));
				r.setBoard_writer(rs.getString("board_writer"));
				r.setBoard_contents(rs.getString("board_contents"));
				r.setBoard_date(rs.getTimestamp("board_date"));
				r.setBoard_password(rs.getString("board_password"));
				r.setBoard_viewcount(rs.getInt("board_viewcount"));
				list.add(r);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return list;
	}
	
	
	 public static void main(String[] args) { // for the test
		RepBoardDAO dao = new RepBoardDAO();
//		RepBoard repBoard = new RepBoard();
//		repBoard.setBoard_subject("테스트 제목");
//		repBoard.setBoard_writer("test");
//		repBoard.setBoard_contents("테스트 내용");
//		repBoard.setBoard_password("testp");
//		repBoard.setParent_seq(1); // 답글쓰기용 테스트

//		try {
//			dao.insert(repBoard); // 글쓰기용 테스트
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		for(RepBoard repBoard:dao.selectByRows(11,20)) {
			System.out.println(repBoard);
		}
	}

	public int selectTotalCnt() {
		String selectTotalCntSQL = "SELECT count(*) FROM repboard";
		int totalCnt = -1;
		
		try {
			
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			
		}
		return totalCnt;
	}
}

