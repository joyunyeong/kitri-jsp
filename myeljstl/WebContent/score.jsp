<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
int totalCnt;//총요청횟수
int starSum; //별점합  
%>    
<%
totalCnt++;
int star = Integer.parseInt(request.getParameter("star"));
starSum += star;
%>
참여자수:<%=totalCnt%><br>
총 별점 :<%=starSum%>
