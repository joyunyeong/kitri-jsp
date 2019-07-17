package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.service.CustomerService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerService service = new CustomerService();
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String result = service.login(id, pass);
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");
		if(result.equals("1")) {//성공
			session.setAttribute("loginInfo", id);
		}
		request.setAttribute("result", result);
		String path = "/loginresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
