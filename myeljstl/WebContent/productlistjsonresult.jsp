<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.kitri.dto.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% List<Product> list = 
         (List)request.getAttribute("productlist");
   JSONArray array = new JSONArray();   
   for(Product p: list){
	   JSONObject obj = new JSONObject();
	   obj.put("cate_no", p.getProductCategory().getCate_no());
	   obj.put("cate_name", p.getProductCategory().getCate_name());
	   obj.put("prod_no", p.getProd_no());
	   obj.put("prod_name", p.getProd_name());
	   obj.put("prod_price",p.getProd_price());
	   obj.put("prod_detail", p.getProd_detail());
	   System.out.println(obj);
	   
	   array.add(obj);
   }
%><%=array.toJSONString()%>