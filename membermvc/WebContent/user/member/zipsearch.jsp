<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
var ziplistView;

function zipSearch() {
	ziplistView = document.getElementById("zip_codeList");
	var doro = document.getElementById("doro").value;
	if(doro.length == 0) {
		alert("검색 도로명을 입력!!!");
		return;
	} else {
		var params = "act=zipsearch&doro=" + doro;
		sendRequest("<%=root%>/user", params, zipsearchResult, "GET");
	}
}

function zipsearchResult() {
	if(httpRequest.readyState == 4) {
		if(httpRequest.status == 200) {
			var view = "";
			var result = httpRequest.responseXML;
			//alert(result);
			var ziplist = result.getElementsByTagName("zip");
			var len = ziplist.length;
			for(var i=0;i<len;i++) {
				var zipcode = ziplist[i].getElementsByTagName("zipcode")[0].firstChild.data;
				var address = ziplist[i].getElementsByTagName("address")[0].firstChild.data;
				view += '<tr>\n';
				view += '	<td>' + zipcode + '</td>\n';
				view += '	<td align="left">';
				view += '<a href="javascript:selectZip(\'' + zipcode + '\', \'' + address + '\');">';
				// 위에 '' 사이 공백 띄지않게 주의할 것
				view += address;
				view += '</a>';
				view += '	</td>\n';
				view += '</tr>\n';
			}
			ziplistView.innerHTML = view;
		}
	} else {
		ziplistView.innerHTML = '<img src="<%=root%>/img/loading.gif" width="80">';
	}
}

function selectZip(z, a) {
	document.getElementById("zipcode").value = z;
	document.getElementById("address").value = a;
	
	//document.getElementById("zipModal").modal("hide");
	$("#zipModal").modal("hide");
	$("#doro").val('');
	$("#zip_codeList").empty();
}
</script>

<div id="zipModal" class="modal fade" role="dialog">
	<h5 class="modal-title" id="myModalLabel">우편번호검색</h5>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>    
            <div class="modal-body text-center">
           		<div align="center">
           			<label>도로명 주소검색</label>
           		</div>
				<div class="input-group" align="left">
					<input type="text" class="form-control" id="doro" name="doro" placeholder="검색 할 도로명 입력(예: 구로디지털로, 여수울로)">
					<span class="input-group-btn">
					<input type="button" class="btn btn-warning" value="검색" id="searchBtn" onclick="javascript:zipSearch();">
					</span>
				</div>
                <div style="width:100%; height:200px; overflow:auto">
                	<table class = "table text-center">
                		<thead>
                		<tr>
                			<th style="width:150px;">우편번호</th>
                			<th style="width:900px;">주소</th>
                		</tr>
                		</thead>
                		<tbody id="zip_codeList"></tbody>
                	</table>
                </div>
            </div>
        </div>
    </div>
</div>