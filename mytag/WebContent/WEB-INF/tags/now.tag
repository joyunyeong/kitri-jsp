<!-- tag 전용 파일에서만 쓰이는 지시자 (<%@tag %>)-->

<%@tag import="java.util.Date"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@ tag body-content="empty" pageEncoding="UTF-8"%>
<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>