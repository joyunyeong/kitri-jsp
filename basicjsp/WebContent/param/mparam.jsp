<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String name = request.getParameter("name");//��ȿ��
	String id = request.getParameter("id");
	int age = Integer.parseInt(request.getParameter("age"));
	String fruit[] = request.getParameterValues("fruit");
	
	String color = age == 10 ? "pink" : "cyan";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%=name%> (<font color="<%=color%>"><%=id%></font>)님 안녕하세요.<br>
	당신이 좋아하는 과일은
	<%
	if(fruit != null) {
		int len = fruit.length;
		for(int i=0;i<len;i++) {
			out.print(fruit[i]);
			if(i != len - 1)
				out.print(", ");
		}
		out.println("입니다.");
	} else {		
		out.println("없습니다.");
	}
%>
</body>
</html>