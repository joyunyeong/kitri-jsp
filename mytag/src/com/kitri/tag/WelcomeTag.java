package com.kitri.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class WelcomeTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException { // tag가 시작되자마자
		System.out.println("WelcomeTag doStartTag");
		JspWriter out = this.pageContext.getOut(); // 지금 사용중인 jsp에서 내장객체를 찾아라
		try {
			out.write("WELCOME");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag(); // index.jsp 화면에 WELCOME 찍힘
	}
	
	@Override
	public int doEndTag() throws JspException { // tag가 끝날 때
		System.out.println("WelcomeTag doEndTag");
		return super.doEndTag();
	}
}
