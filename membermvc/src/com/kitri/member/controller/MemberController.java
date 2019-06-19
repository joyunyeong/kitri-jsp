package com.kitri.member.controller;

import javax.servlet.http.*;

import com.kitri.member.model.MemberDetailDto;
import com.kitri.member.model.MemberDto;
import com.kitri.member.model.service.MemberServiceImpl;

public class MemberController {
	
	private static MemberController memberController;
	
	static { 
		memberController = new MemberController();
	}
	
	private MemberController() {}

	public static MemberController getMemberController() {
		return memberController;
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String path = "/index.jsp";
		
		MemberDetailDto memberDetailDto = new MemberDetailDto();
		memberDetailDto.setName(request.getParameter("name"));
		memberDetailDto.setId(request.getParameter("id"));
		memberDetailDto.setPass(request.getParameter("pass"));
		memberDetailDto.setEmailid(request.getParameter("emailid"));
		memberDetailDto.setEmaildomain(request.getParameter("emaildomain"));
		memberDetailDto.setTel1(request.getParameter("tel1"));
		memberDetailDto.setTel2(request.getParameter("tel2"));
		memberDetailDto.setTel3(request.getParameter("tel3"));
		memberDetailDto.setZipcode(request.getParameter("zipcode"));
		memberDetailDto.setAddress(request.getParameter("address"));
		memberDetailDto.setAddressDetail(request.getParameter("address_detail"));
		
		int cnt = MemberServiceImpl.getMemberService().registerMember(memberDetailDto);
		if(cnt != 0) {
			request.setAttribute("userInfo", memberDetailDto);
			path = "/user/member/registerok.jsp";
		} else {
			path = "/user/member/registerfail.jsp";
		}
		return path;
	}

	public String login(HttpServletRequest request, HttpServletResponse response) {
		String path = "/index.jsp";
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		MemberDto memberDto = MemberServiceImpl.getMemberService().loginMember(id, pass);
		if(memberDto != null) {
			///////////////////////////////////// COOKIE /////////////////////////////////
			
			String idsv = request.getParameter("idsave");
			
			if("idsave".equals(idsv)) {
				Cookie cookie = new Cookie("kid_inf", id); // cookie는 client에 저장됨
				cookie.setDomain("localhost");
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(60*60*24*365*50);
				response.addCookie(cookie); // 쿠키를 보낸다.
			} else { // id 저장 체크 풀었다면
				// 쿠키를 제거시켜라
				Cookie cookie[] = request.getCookies();
				if(cookie != null) {
					for(Cookie c : cookie) { // cookie의 length만큼 for문 돌려도됨
						if("kid_inf".equals(c.getName())) { 
							c.setDomain("localhost");
							c.setPath(request.getContextPath());
							c.setMaxAge(0); // 바로 죽어라 > 만료시간 : 현재 날짜 
							response.addCookie(c); // c담기
							
							break;
						}
					}	
				}
				
			}
			
			
			///////////////////////////////////// SESSION /////////////////////////////////
			
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", memberDto);
			
			//////////////////////////////////////////////////////////////////////////////
			path = "/user/login/loginok.jsp";
		} else { 
			path = "/user/login/loginfail.jsp";
		}
		return path;
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
//		session.setAttribute("userInfo", null);
//		session.removeAttribute("userInfo");
		
		session.invalidate(); // 장바구니에 있는것들을 지워라 > 세션에 있는것들을 모두 지워라
		
		
		return "/user/login/login.jsp";
	}
	
}







