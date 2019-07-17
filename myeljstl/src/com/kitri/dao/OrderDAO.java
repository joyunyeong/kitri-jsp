package com.kitri.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kitri.dto.OrderInfo;
import com.kitri.dto.OrderLine;
import com.kitri.dto.Product;
import com.kitri.exception.AddException;

public class OrderDAO {
	
	public void insert(OrderInfo info) throws AddException{
		Connection conn = null;
		try {
			// 1.JDBC Driver 로드			
			Class.forName("oracle.jdbc.driver.OracleDriver");
	
			// 2.DB연결
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String user = "kitri";
			String password = "kitri";
			
			conn = DriverManager.getConnection(url,user,password);
			conn.setAutoCommit(false); // autoCommit 해제시키기
			
			insertInfo(conn, info); // 주문 기본 추가하기
			
			List<OrderLine> lines = info.getLines();
			insertLine(conn, lines);
			conn.commit(); // 성공했을 때 커밋시키기
			
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException el) {
				el.printStackTrace();
			}
			e.printStackTrace();
			throw new AddException("주문추가 오류" + e.getMessage());
		} finally {
			// DBClose.close(conn, null); > 효인강사님꺼 메소드 쓸 경우
			// DB닫기
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void insertInfo(Connection conn, OrderInfo info) throws SQLException { // 같은 Connection 써야한다 > sequence 때문에!
		PreparedStatement pstmt = null;
		String insertInfoSQL = "INSERT INTO ORDER_INFO(order_no, order_id) " + "VALUES (order_seq.NEXTVAL, ?)";
		try {
			pstmt = conn.prepareStatement(insertInfoSQL);
			pstmt.setString(1, info.getCustomer().getId());
			pstmt.executeUpdate();
		//} catch (SQLException e) {
		//	e.printStackTrace();
		} finally {
			// DBClose.close(conn, null); > 효인강사님꺼 메소드 쓸 경우
			// DB닫기
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void insertLine(Connection conn, List<OrderLine> lines) throws SQLException { // 위에 List<OrderLine>이 list의 형태로 되어 있으니 받아오는것도 list로 만들기! 
		PreparedStatement pstmt = null;
		String insertLineSQL = "INSERT INTO order_line(order_no, order_prod_no, order_quantity) " + "VALUES (order_seq.CURRVAL, ?, ?)";
		System.out.println("먹히나요");
		try {
			pstmt = conn.prepareStatement(insertLineSQL);
			for(OrderLine line:lines) {
//				String prod_no = line.getProd_no();
				String prod_no = line.getProduct().getProd_no();
				pstmt.setString(1, prod_no);
				
				int quantity = line.getOrder_quantity();
				pstmt.setInt(2, quantity);
				// pstmt.executeUpdate();
				pstmt.addBatch(); // 반복수행할때마다 insert할 구문들을 쌓아놓는다. > 일괄처리에 추가
				}
			pstmt.executeBatch(); // 일괄처리
//		} catch (SQLException e) {
//			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<OrderInfo>selectById(String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderInfo> list = new ArrayList<>();
		try {
			//1)JDBC드라이버로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)DB연결
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String user = "kitri";
			String password = "kitri";
			con = DriverManager.getConnection(url, user, password);
			
			//3)SQL송신
			String selectByIdSQL = "SELECT info.order_no, order_dt," + 
					"prod_no, prod_name, prod_price," + 
					" order_quantity" + 
					" FROM order_info info JOIN order_line line ON info.order_no = line.order_no" + 
					" JOIN product p ON p.prod_no = line.ORDER_PROD_NO" + 
					" WHERE order_id = ?" + 
					" ORDER BY order_no DESC, prod_no";
			
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			OrderInfo info=null;
			OrderLine line=null;
			List<OrderLine> lines=null;
			int old_order_no = -1; // 이전 주문번호
			while( rs.next() ) {
				int order_no = rs.getInt("order_no");
				if(old_order_no != order_no) { // 주문번호가 다를 때
					info = new OrderInfo();
					list.add(info); 
					
					info.setOrder_no(order_no);
					info.setOrder_dt(rs.getDate("order_dt"));
					lines = new ArrayList<>();
					info.setLines(lines);
					old_order_no = order_no;
				}
				line = new OrderLine();
				String prod_no = rs.getString("prod_no");// 상품번호,명,가격
				Product p = new Product();
				p.setProd_no(prod_no);
				//:
				line.setProduct(p);
				line.setOrder_quantity(rs.getInt("order_quantity"));
				
				lines.add(line);//???
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return list;
	}	
	public static void main(String[] args) {
		OrderDAO dao = new OrderDAO();
		List<OrderInfo> list = dao.selectById("kitri");
		System.out.println(list);
	}
}
