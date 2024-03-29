package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kitri.dto.Customer;
import com.kitri.dto.OrderInfo;
import com.kitri.dto.OrderLine;
import com.kitri.dto.Product;
import com.kitri.exception.AddException;
import com.kitri.service.OrderService;

@WebServlet("/addorder")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService service;
	
	public AddOrderServlet() {
		service = new OrderService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 장바구니 정보가 주문 테이블에 저장
		OrderInfo info = new OrderInfo();
		HttpSession session = request.getSession(); // servlet에서는 session이 제공되지 않아서 일일이 만들어줘야함
		String id = (String) session.getAttribute("loginInfo");
		Customer c = new Customer();
		c.setId(id);
		info.setCustomer(c); // 주문자 ID 설정
		
		// 장바구니 상품번호, 수량->OrderLine에 설정
		Map<Product, Integer> cart = (Map) session.getAttribute("cart");
		List<OrderLine> lines = new ArrayList<>();
		for(Product p:cart.keySet()) {
			//String no = p.getProd_no();
			int quantity = (Integer)cart.get(p); // no가 아니라 p인 이유는?? no가 인자값인 상태에서 위에 주석 풀면 에러는 안뜨는디..
			OrderLine line = new OrderLine();
			// 상품번호, 수량-> OrderLine에 설정
			// line.setProd_no(prod_no);
			line.setProduct(p);
			line.setOrder_quantity(quantity);
		
		}
		info.setLines(lines);
		String result = "";
		try {
			service.addOrder(info); // 주문이 성공되면 장바구니를 비우고 성공되지 않으면 장바구니 냅두기
			session.removeAttribute("cart"); // 장바구니 비우기
			result = "1";
		} catch (AddException e) {
			e.printStackTrace();
			result = "-1";
		}
		String path = "/addorderresult.jsp";
		request.setAttribute("result", result);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}


}
