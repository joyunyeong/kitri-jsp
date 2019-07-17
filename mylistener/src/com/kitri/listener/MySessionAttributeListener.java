package com.kitri.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MySessionAttributeListener
 *
 */
@WebListener
public class MySessionAttributeListener implements HttpSessionAttributeListener {
	private int logincnt;
    public MySessionAttributeListener() {
    	
    }

    public void attributeAdded(HttpSessionBindingEvent e)  { 
    	String attrName = e.getName(); // 지금 추가된 세션의 네임 얻어오긔
    	if(attrName.equals("loginInfo")) {
    		logincnt++;
    		System.out.println(e.getValue() + "님이 로그인하셨습니다!");
    		System.out.println("로그인된 사용자 수 : " + logincnt);
    	}
    }

    public void attributeRemoved(HttpSessionBindingEvent e)  { // logout되는 시점 
    	String attrName = e.getName(); 
    	if(attrName.equals("loginInfo")) {
    		logincnt--;
    		System.out.println(e.getValue() + "님이 로그아웃하셨습니다.");
    		System.out.println("로그인된 사용자 수 : " + logincnt);
    	}
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 

    }
	
}
