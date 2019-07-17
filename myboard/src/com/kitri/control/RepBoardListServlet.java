package com.kitri.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.dto.PageBean;
import com.kitri.dto.RepBoard;
import com.kitri.service.RepBoardService;

@WebServlet("/boardlist")
public class RepBoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RepBoardService service;
	
	public RepBoardListServlet() {
		service = new RepBoardService();
	}
	// 앞에 슬래시 한것은 JAVABIN에서 대체하는것들!
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 현재 페이지, startRow, endRow
		// 1		1			10
		// 2		11			20
		// 3    	21
		// 5		41			50
		
		// 현재페이지 이전 시작페이지 끝페이지 다음
		// 	  1	   X	1		3	O
		//	  2	   X	1		3	O
		//    3	   X	1		3	O
		// 	  4	   O    4  		6	O
		// 	  5	   O    4  		6	O
		// 	  6	   O    4  		6	O
		// 	  10   O    10     10	X
		
		String cp = request.getParameter("currentPage"); // where is currentPage?
		int currentPage = 1; // 보여줄 페이지
		
		if(cp != null) {
			currentPage = Integer.parseInt(cp);
		}
		
		int cntPerPage = 10; // 페이지별 보여줄 목록 수
		int totalCnt = service.getTotalCnt(); // 총 게시글 수
		int cntPerPageGroup=3; // 페이지 그룹에 보여줄 페이지수
		String url = "boardlist";
		PageBean pb = new PageBean(cntPerPage, totalCnt, cntPerPageGroup, url, currentPage);

//		List<RepBoard> list = service.findByRows(startRow, endRow);
		List<RepBoard> list = service.findByRows(pb.getStartRow(), pb.getEndRow());
		
		pb.setList(list);
		request.setAttribute("pb", pb);
				
		String path = "/boardlistresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
