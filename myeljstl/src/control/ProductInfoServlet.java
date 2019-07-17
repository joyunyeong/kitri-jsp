package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.Product;
import com.kitri.exception.NotFoundException;
import com.kitri.service.ProductService;

@WebServlet("/productinfo")
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService service;
	public ProductInfoServlet() {
		service = new ProductService();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		Product p;
		try {
			p = service.findByNo(no);
			request.setAttribute("product", p);			
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		String path = "/productinforesult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
