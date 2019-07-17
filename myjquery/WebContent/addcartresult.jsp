<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
$(function(){
	var arr = $("div.addcartresult>button");
	$(arr[0]).click(function(){
		alert("상품목록으로가기 클릭했습니다.");
		//메뉴중 상품목록메뉴찾기
		//                                강제 클릭이벤트 발생시키기
		$("nav>ul>li>a[href=productlist]").trigger("click");
		return false;
	});
	$(arr[1]).click(function(){
		alert("장바구니보기를 클릭했습니다.");
		//메뉴중 장바구니보기메뉴찾기
		//                                강제 클릭이벤트 발생시키기
		$("nav>ul>li>a[href=viewcart]").trigger("click");
		return false;
	});
});
</script>    
<div class="addcartresult"
     style="position:absolute;top:200px;
           left:100px;width:250px; 
           border:1px solid;
           background-color: yellow;
           ">
장바구니에 넣었습니다
 <button>상품목록으로가기</button>
 <button>장바구니보기</button>
</div>