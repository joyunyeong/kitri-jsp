<%@page contentType="text/html;charset=UTF-8" %><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

<title>semantic.jsp</title>
<style>
header{background-image: url("images/logo.png");
       background-repeat: no-repeat;
       background-position: right;
       height : 100px;
}
nav{  
      text-align: center;
      background-color: #ACF3FF;
      font-weight: bold;
}
nav>ul{list-style: none;
       padding: 0px;
       margin : 10px;
}
nav>ul>li{display: inline-block; 
       margin:20px;      
}
nav>ul>li>a{text-decoration: none;
            color: #000000;
}
nav>ul>li>a:hover{ text-decoration: none;
		background-color:yellow;
}
footer{
 position:fixed;
 bottom: 0px; 
 width: 100%;
 background-color: #b4b4b4;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script>
$(function(){
	//dom트리에서 nav>ul>li>a객체들 찾기
	var aArr = $("nav>ul>li>a");
	$(aArr).click(function(){
		var vurl = $(this).attr("href");
		if(vurl =='logout'){
			$.ajax({
				url: vurl,
				method:'get',
				success:function(result){
					alert(result.trim());
					location.href="semantic.jsp";
				}
			});
		}else if(vurl=='productlistjson'){
			$.ajax({
				url: vurl,				
				dataType: 'json', 
				success:function(jsonobject){
					$("section").empty();
					var $productlist_orign = $(".product_list_json");
					var $productlist = $productlist_orign.clone();
					$productlist.css("display", "block");
					var $ul = $productlist.children("ul");
					var $li_origin = $(".menuDataSet");
					
					 $.each(jsonobject, function(index, el) {
						 $li = $li_origin.clone();	
						 $li.css("display", "block");
						 var cate_no = el.cate_no;
						 var cate_name = el.cate_name;
						 var prod_no = el.prod_no;
						 var prod_name = el.prod_name;						 
						 var prod_price = el.prod_price;
						 $li.find("img").attr("src", "images/"+prod_no+".jpg");
						 $li.find("img").attr("alt", prod_name);
						 $li.find(".category>span").html(cate_name);
						 $li.find(".no>span").html(prod_no);
						 $li.find(".name>span").html(prod_name);
						 $li.find(".price>span").html(prod_price);
						 $ul.append($li);
					 });
					$("section").empty(); //$("section").html('');
					$("section").append($productlist);
					
				}
			});	
		
		}else{
			alert(vurl);
			$.ajax({
				url: vurl,
				method:'get',
				success:function(result){
					$("section").html(result);
				}
			});
		}
		return false;
	});
	});
</script>
</head>
<body>
<header><h1>MOON BUCKS</h1></header>
<nav>메뉴
<jsp:include page="menu.jsp"/>
</nav>
<section>본문</section>
<footer>사업자  대표 : 오문정 </footer>
<%--start productlist--%>
<style>
div.product_list_json{
display:none;
}
div.product_list_json ul > li.menuDataSet{
    float: left;
    position: relative;
    margin: 5px;
    display:none;
}
div.product_list_json ul{
  list-style: none;
}
</style>
<script>
$(function(){
  var $aObj = $("div.product_list_json>ul>li.menuDataSet dl>dt>a");
  
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
<div class="product_list_json">
<ul>
    <li class="menuDataSet" >
      <dl>
        <dt>
          <a href="#">          
            <img src="" alt="">
          </a>
        </dt>
        <dd class="category">카테고리:<span></span></dd>
        <dd class="no">상품번호:<span></span></dd>
        <dd class="name">상품명:<span></span></dd>
        <dd class="price">가격:<span></span></dd>
      </dl> 
    </li>

  </ul>    
</div>

<%--end productlist --%>
</body>
</html>