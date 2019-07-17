<%@page import="com.kitri.dto.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<style>
div.product_list ul > li.menuDataSet{
    float: left;
    position: relative;
    margin: 5px;
}
div.product_list ul{
  list-style: none;
}
</style>
<script>
$(function(){
  var $aObj = $("div.product_list>ul>li.menuDataSet dl>dt>a");
  
  $aObj.click(function(){
	var url = 'productinfo';
	var $dt = $(this).parent();	
	var $span = $dt.siblings("dd.no").find("span");
	var $no = $span.html().trim();//상품번호
	
	$.ajax({
		url:url,
		method:'get',
		data:'no='+$no,
		success:function(result){
			$("section").html(result.trim());
		}
	});
	return false; 
  });
});
</script>    

<div class="product_list">
<ul>
<c:set var="list" value="${requestScope.productlist}" />
<c:forEach var="p" items="${list}" >
    <li class="menuDataSet">
      <dl>
        <dt>
          <a href="#" >          
            <img src="images/${p.prod_no}.jpg" alt="${p.prod_name}이미지입니다">
          </a>
        </dt>
        <dd class="category">카테고리:<span>${p.productCategory.cate_name}</span></dd>
        <dd class="no">상품번호:<span>${p.prod_no}</span></dd>
        <dd class="name">상품명:<span>${p.prod_name}</span></dd>
        <dd class="price">가격:<span>${p.prod_price}</span></dd>
      </dl> 
    </li>
</c:forEach>
  </ul>    
</div>